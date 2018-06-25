package work.jianhang.microweatherbasic.service;

import work.jianhang.microweatherbasic.vo.WeatherResponse;

/**
 * 天气数据服务接口
 *
 * @author jianhanglin
 */
public interface WeatherDataService {

    /**
     * 根据城市ID查询天气数据
     *
     * @param cityId 城市ID
     * @return WeatherResponse
     */
    WeatherResponse getDataByCityId(String cityId);

    /**
     * 根据城市名称查询天气数据
     *
     * @param cityName 城市名称
     * @return WeatherResponse
     */
    WeatherResponse getDataByCityName(String cityName);
}
