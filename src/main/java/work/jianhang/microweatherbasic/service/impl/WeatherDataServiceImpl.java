package work.jianhang.microweatherbasic.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import work.jianhang.microweatherbasic.service.WeatherDataService;
import work.jianhang.microweatherbasic.vo.WeatherResponse;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 天气数据服务实现类
 *
 * @author jianhanglin
 */
@Service
public class WeatherDataServiceImpl implements WeatherDataService {

    private final static Logger logger = LoggerFactory.getLogger(WeatherDataServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final String WEATHER_API = "http://wthrcdn.etouch.cn/weather_mini";

    /**
     * 缓存超时时间
     */
    private final Long TIME_OUT = 1800L;

    @Override
    public WeatherResponse getDataByCityId(String cityId) {
        String uri = WEATHER_API + "?citykey=" + cityId;
        return this.doGetWeatherData(uri);
    }

    @Override
    public WeatherResponse getDataByCityName(String cityName) {
        String uri = WEATHER_API + "?city=" + cityName;
        return this.doGetWeatherData(uri);
    }

    private WeatherResponse doGetWeatherData(String uri) {
        ValueOperations<String, String> ops = this.stringRedisTemplate.opsForValue();
        /**
         * 将调用的uri作为缓存的key
         */
        String key = uri;

        String strBody = null;
        /**
         * 先查缓存，没有再查服务
         */
        if (!this.stringRedisTemplate.hasKey(key)) {
            logger.info("未找到key " + key);
            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
            if (response.getStatusCodeValue() == 200) {
                strBody = response.getBody();
            }
            ops.set(key, strBody, TIME_OUT, TimeUnit.SECONDS);
        } else {
            logger.info("找到key " + key + ",value=" + ops.get(key));
            strBody = ops.get(key);
        }

        ObjectMapper mapper = new ObjectMapper();
        WeatherResponse weather = null;
        try {
            weather = mapper.readValue(strBody, WeatherResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return weather;
    }
}
