package com.makenv.mapdc.op;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geotools.data.FeatureSource;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.makenv.mapdc.Globals;
import com.makenv.mapdc.VectorMapDataProcessor;
import com.makenv.mapdc.code.CodeService;
import com.makenv.mapdc.code.CountryType;
import com.makenv.mapdc.data.RegionData;
import com.makenv.mapdc.util.StringUtil;
import com.vividsolutions.jts.geom.Envelope;

/**
 * 
 * @description: 针对复合区域地图操作
 * @author alei
 * @date 2014年9月22日
 */
public class ComplexPolygonOp extends AbstractShapeOp {
	private Map<Integer, List<SimpleFeature>> features;
	private Map<String, RegionData> mapDatas;
	private CountryType country;

	public ComplexPolygonOp(int id, String shapefile, CountryType country) {
		super(id, shapefile);
		features = new HashMap<>();
		mapDatas = new HashMap<>();
		this.country = country;
	}

	public void init() throws Exception {
		ShapefileDataStore _shpDataStore = new ShapefileDataStore(new File(shapefile).toURI().toURL());
		_shpDataStore.setCharset(Charset.forName(charset));
		String typeName = _shpDataStore.getTypeNames()[0];
		FeatureSource<SimpleFeatureType, SimpleFeature> featureSource = null;
		featureSource = (FeatureSource<SimpleFeatureType, SimpleFeature>) _shpDataStore.getFeatureSource(typeName);
		FeatureCollection<SimpleFeatureType, SimpleFeature> _featureCollection = featureSource.getFeatures();
		FeatureIterator<SimpleFeature> _featureIterator = _featureCollection.features();
		while (_featureIterator.hasNext()) {
			SimpleFeature _feature = _featureIterator.next();
			int _provCode = (int) _feature.getAttribute(4);
			List<SimpleFeature> _features;
			if (features.containsKey(_provCode)) {
				_features = features.get(_provCode);
			} else {
				_features = new ArrayList<>();
				features.put(_provCode, _features);
			}
			_features.add(_feature);
		}
		_featureIterator.close();
		_shpDataStore.dispose();
	}

	@Override
	public void processData() {
		CodeService _codeService = Globals.getCodeService();
		for (int _key : features.keySet()) {
			List<SimpleFeature> _features = features.get(_key);
			Envelope _envelope = mergeAndProjEnvelope(_features);
			VectorMapDataProcessor _processor = new VectorMapDataProcessor(_features, _envelope, this.getOpType());
			_processor.doPorocess();
			String _code = _codeService.getCode(country, _key);
			if (StringUtil.isEmpty(_code)) {
				// _code = String.valueOf(_key);
				continue;
			}
			mapDatas.put(_code, _processor.getMapData());
		}
	}

	@Override
	public Object getMapData() {
		return mapDatas;
	}

	@Override
	public OpType getOpType() {
		return OpType.COMPLEX_POLYGON;
	}

}
