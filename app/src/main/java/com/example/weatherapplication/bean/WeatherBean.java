package com.example.weatherapplication.bean;

import java.util.List;

public class WeatherBean {
    /**
     * error : 0
     * status : success
     * date : 2019-12-15
     * results : [{"currentCity":"沈阳","pm25":"60","index":[{"des":"天气冷，建议着棉服、羽绒服、皮夹克加羊毛衫等冬季服装。年老体弱者宜着厚棉衣、冬大衣或厚羽绒服。","tipt":"穿衣指数","title":"穿衣","zs":"冷"},{"des":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。","tipt":"洗车指数","title":"洗车","zs":"较适宜"},{"des":"寒冷潮湿，易发生感冒，请注意适当增加衣服，加强自我防护避免感冒。","tipt":"感冒指数","title":"感冒","zs":"极易发"},{"des":"天气较好，但考虑天气寒冷，风力较强，推荐您进行室内运动，若在户外运动请注意保暖并做好准备活动。","tipt":"运动指数","title":"运动","zs":"较不宜"},{"des":"属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。","tipt":"紫外线强度指数","title":"紫外线强度","zs":"中等"}],"weather_data":[{"date":"周日 12月15日 (实时：0℃)","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/duoyun.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/duoyun.png","weather":"多云","wind":"西南风3-4级","temperature":"5 ~ -3℃"},{"date":"周一","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/yujiaxue.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/zhongxue.png","weather":"雨夹雪转中雪","wind":"南风4-5级","temperature":"3 ~ -4℃"},{"date":"周二","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/qing.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/qing.png","weather":"晴","wind":"无持续风向4-5级","temperature":"-3 ~ -15℃"},{"date":"周三","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/qing.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/qing.png","weather":"晴","wind":"北风3-4级","temperature":"-6 ~ -14℃"}]}]
     */

    private int error;
    private String status;
    private String date;
    private List<ResultsBean> results;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * currentCity : 沈阳
         * pm25 : 60
         * index : [{"des":"天气冷，建议着棉服、羽绒服、皮夹克加羊毛衫等冬季服装。年老体弱者宜着厚棉衣、冬大衣或厚羽绒服。","tipt":"穿衣指数","title":"穿衣","zs":"冷"},{"des":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。","tipt":"洗车指数","title":"洗车","zs":"较适宜"},{"des":"寒冷潮湿，易发生感冒，请注意适当增加衣服，加强自我防护避免感冒。","tipt":"感冒指数","title":"感冒","zs":"极易发"},{"des":"天气较好，但考虑天气寒冷，风力较强，推荐您进行室内运动，若在户外运动请注意保暖并做好准备活动。","tipt":"运动指数","title":"运动","zs":"较不宜"},{"des":"属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。","tipt":"紫外线强度指数","title":"紫外线强度","zs":"中等"}]
         * weather_data : [{"date":"周日 12月15日 (实时：0℃)","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/duoyun.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/duoyun.png","weather":"多云","wind":"西南风3-4级","temperature":"5 ~ -3℃"},{"date":"周一","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/yujiaxue.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/zhongxue.png","weather":"雨夹雪转中雪","wind":"南风4-5级","temperature":"3 ~ -4℃"},{"date":"周二","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/qing.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/qing.png","weather":"晴","wind":"无持续风向4-5级","temperature":"-3 ~ -15℃"},{"date":"周三","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/qing.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/qing.png","weather":"晴","wind":"北风3-4级","temperature":"-6 ~ -14℃"}]
         */

        private String currentCity;
        private String pm25;
        private List<IndexBean> index;
        private List<WeatherDataBean> weather_data;

        public String getCurrentCity() {
            return currentCity;
        }

        public void setCurrentCity(String currentCity) {
            this.currentCity = currentCity;
        }

        public String getPm25() {
            return pm25;
        }

        public void setPm25(String pm25) {
            this.pm25 = pm25;
        }

        public List<IndexBean> getIndex() {
            return index;
        }

        public void setIndex(List<IndexBean> index) {
            this.index = index;
        }

        public List<WeatherDataBean> getWeather_data() {
            return weather_data;
        }

        public void setWeather_data(List<WeatherDataBean> weather_data) {
            this.weather_data = weather_data;
        }

        public static class IndexBean {
            /**
             * des : 天气冷，建议着棉服、羽绒服、皮夹克加羊毛衫等冬季服装。年老体弱者宜着厚棉衣、冬大衣或厚羽绒服。
             * tipt : 穿衣指数
             * title : 穿衣
             * zs : 冷
             */

            private String des;
            private String tipt;
            private String title;
            private String zs;

            public String getDes() {
                return des;
            }

            public void setDes(String des) {
                this.des = des;
            }

            public String getTipt() {
                return tipt;
            }

            public void setTipt(String tipt) {
                this.tipt = tipt;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getZs() {
                return zs;
            }

            public void setZs(String zs) {
                this.zs = zs;
            }
        }

        public static class WeatherDataBean {
            /**
             * date : 周日 12月15日 (实时：0℃)
             * dayPictureUrl : http://api.map.baidu.com/images/weather/day/duoyun.png
             * nightPictureUrl : http://api.map.baidu.com/images/weather/night/duoyun.png
             * weather : 多云
             * wind : 西南风3-4级
             * temperature : 5 ~ -3℃
             */

            private String date;
            private String dayPictureUrl;
            private String nightPictureUrl;
            private String weather;
            private String wind;
            private String temperature;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getDayPictureUrl() {
                return dayPictureUrl;
            }

            public void setDayPictureUrl(String dayPictureUrl) {
                this.dayPictureUrl = dayPictureUrl;
            }

            public String getNightPictureUrl() {
                return nightPictureUrl;
            }

            public void setNightPictureUrl(String nightPictureUrl) {
                this.nightPictureUrl = nightPictureUrl;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public String getWind() {
                return wind;
            }

            public void setWind(String wind) {
                this.wind = wind;
            }

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }
        }
    }
}
