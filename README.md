# weather-simulator
Toy wealther simulation will genearte fake weather data based on the real weather data from Bureau of Meteorlogy website.
http://www.bom.gov.au/climate/data/

Choose 3 obeservation points(1 for Sydney, 1 for Melbourne, 1 for Adelaide)

http://www.bom.gov.au/jsp/ncc/cdio/calendar/climate-calendar?stn_num=066062&month=03&day=30
 <table class="tg">
  <tr>
    <th class="tg-yw4l"><b>Site Name:</b></th>
    <th class="tg-yw4l">Sydney (Observatory Hill)</th>
  </tr>
  <tr>
    <th class="tg-yw4l"><b>Site Code:</b></th>
    <th class="tg-yw4l">066062</th>
  </tr>
  <tr>
    <th class="tg-yw4l"><b>Geographic Location:</b></th>
    <th class="tg-yw4l">-33.86, 151.2</th>
  </tr>
  <tr>
    <th class="tg-yw4l"><b>Elevation:</b></th>
    <th class="tg-yw4l">39</th>
  </tr>
</table>

http://www.bom.gov.au/jsp/ncc/cdio/calendar/climate-calendar?stn_num=086038&month=03&day=30
<table class="tg">
  <tr>
    <th class="tg-yw4l"><b>Site Name:</b></th>
    <th class="tg-yw4l">Melbourne (Essendon Airport)</th>
  </tr>
  <tr>
    <th class="tg-yw4l"><b>Site Code:</b></th>
    <th class="tg-yw4l">086038</th>
  </tr>
  <tr>
    <th class="tg-yw4l"><b>Geographic Location:</b></th>
    <th class="tg-yw4l">-37.73, 144.91</th>
  </tr>
  <tr>
    <th class="tg-yw4l"><b>Elevation:</b></th>
    <th class="tg-yw4l">78</th>
  </tr>
</table>

http://www.bom.gov.au/jsp/ncc/cdio/calendar/climate-calendar?stn_num=023000&month=03&day=30
<table class="tg">
  <tr>
    <th class="tg-yw4l"><b>Site Name:</b></th>
    <th class="tg-yw4l">Adelaide (West Terrace / Ngayirdapira)</th>
  </tr>
  <tr>
    <th class="tg-yw4l"><b>Site Code:</b></th>
    <th class="tg-yw4l">023000</th>
  </tr>
  <tr>
    <th class="tg-yw4l"><b>Geographic Location:</b></th>
    <th class="tg-yw4l">-34.93, 138.58</th>
  </tr>
  <tr>
    <th class="tg-yw4l"><b>Elevation:</b></th>
    <th class="tg-yw4l">29</th>
  </tr>
</table>

```
Ps. Not encourage for using UDFs in real work unless there is no other options.
    To make emulation weather design clear and consist, therefore, adopt UDFs. 
```
Bom(Bureau of Meteorlogy) website has the daily max, min temprature statis from history observation data.
Therfore, the emulated day temperature must be in that range.

```
Weather-sumulator workflow: 
 1. Extract the statis daily data by looping emulate date range
 2. Using Spark SQL + UDFs to generate the emulated data for the same observation location.
 3. Genearate verify output by listing statis daily maxm min temperature and emulated temperature in same row.
 4. Virtulize the output using excel
```








Ps. Not encourage for using UDFs in real work unless there is no other options.
To make emulation weather design clear and consist, therefore, adopt UDFs. 
 
Toy wealther simulation will genearte report data based on study of the real weather data based on input from API.

http://history.openweathermap.org/data/2.5/history/city?id=2147714&type=hour&start=1369728000&end=1369789200

http://history.openweathermap.org/data/2.5/history/city?id={id}&type=hour&start={start}&end={end}

https://samples.openweathermap.org/data/2.5/history/city?id=2147714&type=hour&appid=4de065fa58a4680f5ada222fecc20859

http://www.bom.gov.au/jsp/ncc/cdio/weatherData/av?p_display_type=monthlyZippedDataFile&p_stn_num=066062&p_c=-872833409&p_nccObsCode=36&p_startYear=

http://www.bom.gov.au/climate/data/
 
<table class="tg">
  <tr>
    <th class="tg-yw4l"><b>Site Name:</b></th>
    <th class="tg-yw4l">Sydney (Observatory Hill)</th>
  </tr>
  <tr>
    <th class="tg-yw4l"><b>Site Code:</b></th>
    <th class="tg-yw4l">066062</th>
  </tr>
  <tr>
    <th class="tg-yw4l"><b>Geographic Location:</b></th>
    <th class="tg-yw4l">-33.86, 151.2</th>
  </tr>
  <tr>
    <th class="tg-yw4l"><b>Elevation:</b></th>
    <th class="tg-yw4l">39</th>
  </tr>
</table>
 

![picture](src/main/resources/images/sydneyverify.jpg)
![picture](src/main/resources/images/sydneyRainfallTrend.jpg)


 
<table class="tg">
  <tr>
    <th class="tg-yw4l"><b>Site Name:</b></th>
    <th class="tg-yw4l">Melbourne (Essendon Airport)</th>
  </tr>
  <tr>
    <th class="tg-yw4l"><b>Site Code:</b></th>
    <th class="tg-yw4l">086038</th>
  </tr>
  <tr>
    <th class="tg-yw4l"><b>Geographic Location:</b></th>
    <th class="tg-yw4l">-37.73, 144.91</th>
  </tr>
  <tr>
    <th class="tg-yw4l"><b>Elevation:</b></th>
    <th class="tg-yw4l">78</th>
  </tr>
</table>
 

![picture](src/main/resources/images/melbourneverify.jpg)
![picture](src/main/resources/images/melbourneRainfallTrend.jpg)

 
<table class="tg">
  <tr>
    <th class="tg-yw4l"><b>Site Name:</b></th>
    <th class="tg-yw4l">Adelaide (West Terrace / Ngayirdapira)</th>
  </tr>
  <tr>
    <th class="tg-yw4l"><b>Site Code:</b></th>
    <th class="tg-yw4l">023000</th>
  </tr>
  <tr>
    <th class="tg-yw4l"><b>Geographic Location:</b></th>
    <th class="tg-yw4l">-34.93, 138.58</th>
  </tr>
  <tr>
    <th class="tg-yw4l"><b>Elevation:</b></th>
    <th class="tg-yw4l">29</th>
  </tr>
</table>
 

![picture](src/main/resources/images/adelaideverify.jpg)
![picture](src/main/resources/images/adelaideRainfallTrend.jpg)

