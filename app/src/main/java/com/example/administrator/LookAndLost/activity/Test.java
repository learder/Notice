package com.example.administrator.LookAndLost.activity;

import android.content.Context;
import android.util.Log;

import com.example.administrator.LookAndLost.entity.CityEntity;
import com.example.administrator.LookAndLost.utils.PingYinUtil;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 颜厥共 on 2016/2/22.
 * email:644613693@qq.com
 */
public class Test {

    private static String TAG = "getCityDataFromLocation";
    //直辖县，这里把数据从县级修改成市级
    private static String[] ZhiXiaXian={
            "仙桃市","潜江市","天门市","神农架林区",
            "石河子市","阿拉尔市","图木舒克市","五家渠市",
    };

    public static List<CityEntity> getCityDataFromLocation(Context context) {
        List<CityEntity> list = new ArrayList<CityEntity>();
        String res = "";

        String lastName="";
        String fileName = "mnt/sdcard/cc_new.sql";
        try {
//			FileReader fr;
//			fr = new FileReader(fileName);
            InputStreamReader in=new InputStreamReader(new FileInputStream(fileName),"UTF-8");
//			BufferedReader br = new BufferedReader(fr);
            BufferedReader br = new BufferedReader(in);
            String temp = null;
            String s = "";
            while ((temp = br.readLine()) != null) {
                String strs[];
                strs = temp.split(",");
                strs[0] = strs[0].replace(
                        "INSERT INTO `tb_prov_city_area_street` VALUES (", "");
                strs[4] = strs[4].replace(");", "");
                for (int i = 0; i < strs.length; i++) {
                    strs[i] = strs[i].trim();
                    strs[i] = strs[i].replace("'", "");
                }
                CityEntity entity = new CityEntity();
                entity.setId(Integer.parseInt(strs[0]));
                int a = Integer.parseInt(strs[1]);
                entity.setCode(a);
                entity.setParentId(Integer.parseInt(strs[2]));
                entity.setFullName(strs[3]);
                entity.setLevel(Integer.parseInt(strs[4]));
                // 使用的数据库区别
                // 在数据库里北京市 作为一个省 和直辖区 第二条只显示直辖区 所以把市辖区更名为 北京市
                if (entity.getFullName().equals("市辖区")&&entity.getLevel()==2) {
                    entity.setFullName(lastName);
                    Log.d(TAG, "直辖市替换为-->"+entity.getFullName());
                }
                //去除市辖区
                if (entity.getFullName().equals("市辖区")&&entity.getLevel()==3) {
                    continue;
                }
                //去除市辖县，省属虚拟市,县
                if (entity.getFullName().equals("市辖县")||entity.getFullName().equals("省属虚拟市")
                        ||entity.getFullName().equals("县")){
                    continue;
                }

                lastName=entity.getFullName();
                // 宁德市拼音转换失败，这里手动添加
                if (entity.getId() == 17219) {
                    entity.setNickName("NDS");
                }else if (entity.getId()==31930) { //重庆市 拼音从 Z 改成 C
                    entity.setNickName("CQS");
                }else if (entity.getId()==16278) { //厦门市 拼音从 S 改成 X
                    entity.setNickName("XMS");
                }else if (entity.getId()==3431) { //长治市 拼音从 Z 改成 C
                    entity.setNickName("CZS");
                }else if (entity.getId()==7532) { //长春市 拼音从 Z 改成 C
                    entity.setNickName("CCS");
                }else {
                    entity.setNickName(PingYinUtil
                            .converterToFirstSpell(entity.getFullName()));
                }
                entity.setHeader(entity.getHeader());

                //更改省直辖县的从2改为1级  市-->省
                if (entity.getFullName().equals("省直辖行政单位")) {
                    entity.setLevel(1);
                }
                //更改直辖县的3级改为2级 县-->市
                for (int i = 0; i < ZhiXiaXian.length; i++) {
                    if (entity.getFullName().equals(ZhiXiaXian[i])) {
                        entity.setLevel(2);
                        Log.d(TAG, "行政等级替换为2===>"+entity.getFullName());
                    }
                }
                //去除省直辖行政单位
                list.add(entity);

            }
            br.close();
            in.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        DbUtils db = DbUtils.create(context, "mnt/sdcard/",
                "prov_city_area_street.db");
        db.configDebug(true);
        db.configAllowTransaction(true);
        try {
            db.saveAll(list);
            Log.d(TAG, "存储成功");
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.d(TAG, "存储失败");
        }
        Log.d(TAG, "list size:" + list.size());
        return list;
    }

    public static void getDB(Context context) {
        DbUtils db = DbUtils.create(context, "mnt/sdcard/",
                "prov_city_area_street.db");
        db.configDebug(true);
        db.configAllowTransaction(true);
        try {
            List<CityEntity> list = db.findAll(CityEntity.class);
            for (int i = 0; i < list.size(); i++) {
                CityEntity entity = list.get(i);
                Log.d(TAG, entity.toString());
            }
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 读取文本文件
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String read(Context context, String fileName) {
        try {
            FileInputStream in = context.openFileInput(fileName);
            return readInStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String readInStream(InputStream inStream) {
        try {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[512];
            int length = -1;
            while ((length = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, length);
            }

            outStream.close();
            inStream.close();
            return outStream.toString("UTF-8");
        } catch (IOException e) {
            Log.i("FileTest", e.getMessage());
        }
        return null;
    }
}
