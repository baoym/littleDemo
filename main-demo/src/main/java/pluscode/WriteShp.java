//package pluscode;
//import java.io.File;
//
//import java.io.Serializable;
//import java.nio.charset.Charset;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import org.geotools.data.FeatureWriter;
//import org.geotools.data.Transaction;
//import org.geotools.data.shapefile.ShapefileDataStore;
//import org.geotools.data.shapefile.ShapefileDataStoreFactory;
//import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
//import org.geotools.referencing.crs.DefaultGeographicCRS;
//import org.opengis.feature.simple.SimpleFeature;
//import org.opengis.feature.simple.SimpleFeatureType;
//import com.vividsolutions.jts.geom.LineString;
//import com.vividsolutions.jts.geom.Point;
//import com.vividsolutions.jts.geom.Polygon;
//
///**
// * @ClassName: pluscode
// * @Author: BYM
// * @Date: 2019/4/15
// * @Description:
// * @Company: 本软件文档资料是北京悦图遥感科技发展有限公司的资产，任何人阅读和使用本资料必须获得相
// * 应的书面授权，承担保密责任和接受相应的法律约束.
// */
//public class WriteShp {
//
//    public static void remove(File dir) {
//        File files[] = dir.listFiles();
//        for (int i = 0; i < files.length; i++) {
//            if(files[i].isDirectory()) {
//                remove(files[i]);
//            }else {
//                files[i].delete();
//            }
//        }
//        //删除目录
//        dir.delete();
//    }
//
////    @Autowired
////    private
//
//    /**
//     * @Author BYM
//     * @Function
//     * @Param [fidList, tableName, featuretype]
//     * fidlist为空时导出全表
//     * @return void
//     **/
//    public static void writeShp(List<Integer> fidList, String tableName,int featuretype) {
//        try {
//            //查询字段名称以及类型
//            String sql1 = "SELECT a.attname AS field,t.typname AS type FROM pg_class c,pg_attribute a,pg_type t WHERE c.relname = \'" + tableName +"\' and a.attnum > 0 and a.attrelid = c.oid and a.atttypid = t.oid;";
//            List<Map<String,Object>> listcolumn = jdbcTemplate.queryForList(sql1);
//
//            String geomFieldName = "";
//            String zipPath = "";
//            //创建实例表生成文件夹
//            File path = new File(zipPath);
//            if(!path.exists()){//如果文件夹不存在
//                path.mkdir();//创建文件夹
//            }
//            File newfile=new File(zipPath + tableName);
//            if(!newfile.exists()){//如果文件夹不存在
//                newfile.mkdir();//创建文件夹
//            }
//            //实例表文件写入地址
//            String filepath = zipPath + tableName+"\\"+tableName+".shp";
//            File file = new File(filepath);
//            Map<String, Serializable> params = new HashMap<String, Serializable>();
//            params.put( ShapefileDataStoreFactory.URLP.key, file.toURI().toURL() );
//            ShapefileDataStore ds = (ShapefileDataStore) new ShapefileDataStoreFactory().createNewDataStore(params);
//            //定义图形信息和属性信息
//            SimpleFeatureTypeBuilder tb = new SimpleFeatureTypeBuilder();
//            //遍历实例表内属性字段名以及字段类型
//            for(Map<String,Object> map1 : listcolumn) {
//                if(map1.get("type").equals("int4")) {
//                    tb.add((String)map1.get("field"), Long.class);
//                }else if(map1.get("type").equals("varchar") || map1.get("type").equals("text")) {
//                    tb.add((String)map1.get("field"), String.class);
//                }else if(map1.get("type").equals("double")) {
//                    tb.add((String)map1.get("field"), Double.class);
//                }else if(map1.get("type").equals("geometry")) {
//                    geomFieldName = (String)map1.get("field");
//                    //遍历实例表的属性(点、线、面)
//                    if(featuretype == 1) {
//                        tb.add((String)map1.get("field"),Point.class);
//                    }else if(featuretype == 2) {
//                        tb.add((String)map1.get("field"),LineString.class);
//                    }else if(featuretype == 3) {
//                        tb.add((String)map1.get("field"),Polygon.class);
//                    }
//                }
//            }
//
//            List<Map<String,Object>> listdate = new ArrayList<Map<String,Object>>();
//            if(fidList != null) {
//                String sql = "select *,st_astext("+geomFieldName+") as geom_text from " + tableName + " where ";
//                for (Integer integer : fidList) {
//                    sql += "\"FID\" ="+integer +" or ";
//                }
//                sql = sql.substring(0, sql.length()-4) + ";";
//                listdate = jdbcTemplate.queryForList(sql);
//            }
//            //如果没有属性条件查询需求按照以下进行
//            else {
//                listdate = jdbcTemplate.queryForList("select *,st_astext(geom) as geom_text from " + tableName);
//            }
//
//            tb.setCRS(DefaultGeographicCRS.WGS84);
//            tb.setName("shapefile");
//            ds.createSchema(tb.buildFeatureType());
//            ds.setStringCharset(Charset.forName("GBK"));
//            //设置Writer
//            FeatureWriter<SimpleFeatureType, SimpleFeature> writer = ds.getFeatureWriter(ds.getTypeNames()[0], Transaction.AUTO_COMMIT);
//            //创建缓冲区
//            SimpleFeature feature = null;
//            //将实例表对应的字段名添加到缓冲区
//            for(Map<String,Object> mapcontrains : listdate) {
//                feature = writer.next();
//                //将实例表对应字段的值添加到缓冲区
//                for(Map<String,Object> map1 : listcolumn) {
//                    if(mapcontrains.get(map1.get("field")) != null) {
//                        if(!map1.get("type").equals("geometry")) {
//                            System.out.println(map1.get("field") + ">>>>>>" + mapcontrains.get(map1.get("field")));
//                            feature.setAttribute((String)map1.get("field"), mapcontrains.get(map1.get("field")));
//                        }
//                        else {
//                            System.out.println("geom_text>>>>>>" + mapcontrains.get("geom_text"));
//                            feature.setAttribute("geom", mapcontrains.get("geom_text"));
//                        }
//                    }
//                }
//            }
//            //将缓冲区内容写入文件
//            writer.write();
//            writer.close();
//            ds.dispose();
//            AntZip.zipT(zipPath + tableName,zipPath + tableName+"\\"+tableName+".zip");
//            if(newfile.exists()) {
//                remove(newfile);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//}
