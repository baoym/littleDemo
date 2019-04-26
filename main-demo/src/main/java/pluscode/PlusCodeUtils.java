//package pluscode;
//
//import com.google.openlocationcode.OpenLocationCode;
//import com.vividsolutions.jts.geom.Coordinate;
//import com.vividsolutions.jts.geom.Geometry;
//import com.vividsolutions.jts.geom.GeometryCollection;
//import com.vividsolutions.jts.geom.GeometryFactory;
//import com.vividsolutions.jts.io.ParseException;
//import org.geotools.geometry.jts.JTSFactoryFinder;
//import org.geotools.geometry.jts.WKTReader2;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @ClassName: pluscode
// * @Author: BYM
// * @Date: 2019/4/4
// * @Description:
// * @Company: 本软件文档资料是北京悦图遥感科技发展有限公司的资产，任何人阅读和使用本资料必须获得相
// * 应的书面授权，承担保密责任和接受相应的法律约束.
// */
//public class PlusCodeUtils {
//
//
//    /**
//     * @Author BYM
//     * @Function 线面获取pluscode，多线和多面是多个pluscode
//     * @Param [wkt]
//     * @return java.util.List<java.lang.String>
//     **/
//    public static List<String> getCodeFromWkt(String wkt) throws ParseException {
//        List<Map> geometryBounds = getGeometryBounds(wkt);
//        List<String> pcodeList = new ArrayList<String>();
//        for (Map geometryBound : geometryBounds) {
//            Double minX = (Double) geometryBound.get("minX");
//            Double minY = (Double) geometryBound.get("minY");
//            Double maxX = (Double) geometryBound.get("maxX");
//            Double maxY = (Double) geometryBound.get("maxY");
//            String code1 = OpenLocationCode.encode(minY,minX);
//            String code2 = OpenLocationCode.encode(minY,maxX);
//            String code3 = OpenLocationCode.encode(maxY,minX);
//            String code4 = OpenLocationCode.encode(maxY,maxX);
//            String[] params = {code1,code2,code3,code4};
//
//            for (String param : params) {
//                System.out.println(param);
//
//            }
//            //获取pluscode交集
//            String commonPrefix = StringUtils.getCommonPrefix(params);
//            pcodeList.add(commonPrefix);
//        }
//        return pcodeList;
//
//    }
//
//    /**
//     * @return com.vividsolutions.jts.geom.Geometry
//     * @Author BYM
//     * @Function 最大外接矩形的四至坐标 多线和多面返回多个
//     * @Param [wktString]
//     **/
//    public static List<Map> getGeometryBounds(String wktString) throws ParseException {
//        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);
////        wktString = "MULTIPOLYGON (((85.47881510416627 29.090789062499965, 101.90155752840892 29.950118607954522, 100.46934161931799 20.68845572916652, 84.81044767992384 22.69355800189382, 85.47881510416627 29.090789062499965),(85.47881510416627 30.090789062499965, 101.90155752840892 29.950118607954522, 100.46934161931799 20.68845572916652, 84.81044767992384 22.69355800189382, 85.47881510416627 30.090789062499965)))";
////        wktString="MULTIPOLYGON (((116.150656269685 40.2555146949705,116.014313154051 40.2233919713918,116.007174771034 40.3468859975945,116.249165955327 40.3761533679661,116.150656269685 40.2555146949705)),((116.204194142317 40.3861471041907,116.104970618373 40.3861471041907,116.108539809882 40.4625278024778,116.214901716843 40.4753768919093,116.204194142317 40.3861471041907)),((116.416917956238 40.4696661854953,116.293423930035 40.4282635639937,116.256304338344 40.5474745603859,116.441188458497 40.578169607361,116.416917956238 40.4696661854953)))MULTIPOLYGON (((116.150656269685 40.2555146949705,116.014313154051 40.2233919713918,116.007174771034 40.3468859975945,116.249165955327 40.3761533679661,116.150656269685 40.2555146949705)),((116.204194142317 40.3861471041907,116.104970618373 40.3861471041907,116.108539809882 40.4625278024778,116.214901716843 40.4753768919093,116.204194142317 40.3861471041907)),((116.416917956238 40.4696661854953,116.293423930035 40.4282635639937,116.256304338344 40.5474745603859,116.441188458497 40.578169607361,116.416917956238 40.4696661854953)))";
////        wktString="MULTIPOLYGON (((423.415815353394 -260.701648712158,100.998846054077 -304.721677780151,155.726449966431 62.9050464630127,423.415815353394 -260.701648712158)),((128.552240371704 147.022390365601,-142.343282699585 -77.5746269226074,-101.462686538696 120.425374984741,128.552240371704 147.022390365601)),((379.395784378052 391.270666122437,457.917997360229 75.9920825958252,39.1328601837158 401.978242874146,379.395784378052 391.270666122437)))";
////        wktString = "POLYGON ((85.47881510416627 29.090789062499965, 101.90155752840892 29.950118607954522, 100.46934161931799 20.68845572916652, 84.81044767992384 22.69355800189382, 85.47881510416627 29.090789062499965),(85.47881510416627 30.090789062499965, 101.90155752840892 29.950118607954522, 100.46934161931799 20.68845572916652, 84.81044767992384 22.69355800189382, 85.47881510416627 30.090789062499965))";
//        WKTReader2 reader = new WKTReader2(geometryFactory);
//
//        Geometry geometry = reader.read(wktString);
//        String geometryType = geometry.getGeometryType();
//
//        List<Map> resultMap = new ArrayList<Map>();
//        if(geometryType.contains("MULTI")) {
//            GeometryCollection polygonFind = (GeometryCollection) reader.read(wktString);
//
//            int numGeometries = polygonFind.getNumGeometries();
//            System.out.println(numGeometries);
//
//            for (int i = 0; i < numGeometries; i++) {
//                Map<String,Double> map = new HashMap<String, Double>();
//                Geometry geometryN = polygonFind.getGeometryN(i);
//                Coordinate[] coordinates = geometryN.getEnvelope().getBoundary().getCoordinates();
//                double minX = coordinates[0].getOrdinate(0);
//                double minY = coordinates[0].getOrdinate(1);
//                double maxX = coordinates[2].getOrdinate(0);
//                double maxY = coordinates[2].getOrdinate(1);
//                map.put("minX",minX);
//                map.put("minY",minY);
//                map.put("maxX",maxX);
//                map.put("maxY",maxY);
//                resultMap.add(map);
//
//            }
//
//        }else{
//            Map<String,Double> map = new HashMap<String, Double>();
//            Coordinate[] coordinates = geometry.getEnvelope().getBoundary().getCoordinates();
//            double minX = coordinates[0].getOrdinate(0);
//            double minY = coordinates[0].getOrdinate(1);
//            double maxX = coordinates[2].getOrdinate(0);
//            double maxY = coordinates[2].getOrdinate(1);
//            map.put("minX",minX);
//            map.put("minY",minY);
//            map.put("maxX",maxX);
//            map.put("maxY",maxY);
//            resultMap.add(map);
//        }
//
//        return resultMap;
//    }
//
//
//
//    public static void main(String[] args) throws ParseException {
//
////        String code = OpenLocationCode.encode(40.00,116.00);
////        System.out.println(code);
////
////        String code1 = OpenLocationCode.encode(40,116);
////        System.out.println(code1);
////
////        String code2 = OpenLocationCode.encode(40, 116, 6);
////        System.out.println(code2);
////
////        OpenLocationCode.CodeArea codeArea =  OpenLocationCode.decode("8PGR2222+22");
////        System.out.println(codeArea.getSouthLatitude() + "\n"
////                + codeArea.getNorthLatitude() + "\n"
////                + codeArea.getWestLongitude() + "\n"
////                + codeArea.getEastLongitude());
//
//        List<String> codeFromWkt = getCodeFromWkt("MULTIPOLYGON (((116.150656269685 40.2555146949705,116.014313154051 40.2233919713918,116.007174771034 40.3468859975945,116.249165955327 40.3761533679661,116.150656269685 40.2555146949705)),((116.204194142317 40.3861471041907,116.104970618373 40.3861471041907,116.108539809882 40.4625278024778,116.214901716843 40.4753768919093,116.204194142317 40.3861471041907)),((116.416917956238 40.4696661854953,116.293423930035 40.4282635639937,116.256304338344 40.5474745603859,116.441188458497 40.578169607361,116.416917956238 40.4696661854953)))MULTIPOLYGON (((116.150656269685 40.2555146949705,116.014313154051 40.2233919713918,116.007174771034 40.3468859975945,116.249165955327 40.3761533679661,116.150656269685 40.2555146949705)),((116.204194142317 40.3861471041907,116.104970618373 40.3861471041907,116.108539809882 40.4625278024778,116.214901716843 40.4753768919093,116.204194142317 40.3861471041907)),((116.416917956238 40.4696661854953,116.293423930035 40.4282635639937,116.256304338344 40.5474745603859,116.441188458497 40.578169607361,116.416917956238 40.4696661854953)))");
//        for (String s : codeFromWkt) {
//            System.out.println(s);
//
//        }
//
//    }
//}
