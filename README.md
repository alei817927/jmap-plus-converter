jmap-plus-converter
======

# 介绍

`jmap-plus-converter` 是 [jmap-plus](https://github.com/alei817927/jmap-plus) 项目的地图数据转化器，基于 [GeoTools](http://www.geotools.org/) 项目开发，生成 `JS` 数据。
# 功能
* 生成单区域数据和复合区域数据
* 生成点数据，作为 `Marker` 使用
* 生成线数据

# 配置
```
#########################################投影信息#########################################
#投影方式 lcc,mill,merc
Projection.proj=mill
Projection.lat1=25
Projection.lat2=40
Projection.lat0=34
Projection.lon0=110
Projection.x0=0
Projection.y0=0
Projection.ellps=WGS84
Projection.units=m
Projection.extra=+no_defs
#########################################生成地图信息#########################################
JVectorMap.width=900
JVectorMap.left=0
JVectorMap.top=0
#--------------------------------------#
JVectorMap.polygonFile=/data/jmapplus-data/jquery-jmapplus-region-{0}-{1}-{2}.js
#--------------------------------------#
JVectorMap.pointFile=/data/jmapplus-data/jquery-jmapplus-marker-{0}-{1}-{2}.js
#--------------------------------------#
JVectorMap.complexPolygonFile=/data/jmapplus-data/jquery-jmapplus-region-{0}-{1}-{2}.js
#----/data---------------#
JVectorMap.lineFile=/data/jmapplus-data/jquery-jmapplus-line-{0}-{1}-{2}.js
#数据精度
JVectorMap.precision=4
#buffer distance
JVectorMap.distance=-0.01
#########################################ShapeFile信息#########################################
#------------------区域数据------------------#
# 为空则不处理
ShapeFile.polygonFile=/data/china.shp
ShapeFile.polygonCharset=GBK
ShapeFile.polygonNameIndex=1
#如果为0则不处理buffer
ShapeFile.polygonBufferDistance=-0.0005f
ShapeFile.polygonLatIndex=-1
ShapeFile.polygonLonIndex=-1
ShapeFile.polygonNameCode=chn
ShapeFile.polygonLanguage=cn
#------------------复合区域数据------------------#
# 为空则不处理
ShapeFile.complexPolygonFile=/data/County_Boundary.shp
ShapeFile.complexPolygonCharset=GBK
ShapeFile.complexPolygonNameIndex=1
#如果为0则不处理buffer
ShapeFile.complexPolygonBufferDistance=-0.0005f
ShapeFile.complexPolygonLatIndex=6
ShapeFile.complexPolygonLonIndex=7
ShapeFile.complexPolygonLanguage=cn
#------------------线数据------------------#
# lineFile和lineDependPolygonFile有一个为空则不处理
ShapeFile.lineFile=/data/highway_polyline.shp
#注意操作路线需要依赖区域文件polygonFile
ShapeFile.lineDependPolygonFile=/data/js_region.shp
#选取线中点的时候，每隔x个选择一个，旨在减少文件大小
ShapeFile.lineInterval=20
ShapeFile.lineCharset=GBK
ShapeFile.lineNameIndex=2
ShapeFile.lineNameCode=js
ShapeFile.lineLanguage=cn
#--------------------------------------#
# 为空则不处理
ShapeFile.pointFile=/data/js_point.shp
ShapeFile.pointCharset=GBK
ShapeFile.pointNameIndex=1
ShapeFile.pointNameCode=js
ShapeFile.pointLanguage=cn
```
# 使用方法

* 修改配置文件 `mapdc.properties`
* 导入依赖的库，进入到项目运行：
```bash
$ mvn dependency:copy-dependencies -DoutputDirectory=lib
```
下载依赖库到lib目录下面
* 运行工具
```bash
java -cp lib/*.jar -jar dist/map-data-converter.jar
```