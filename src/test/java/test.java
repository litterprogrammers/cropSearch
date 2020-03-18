import com.bjh.mapper.SelMapper;
import com.bjh.service.impl.SelServiceImpl;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test {
    @Test
    public void gettest(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Object selStuMapper = context.getBean("selServiceImpl");
        SelServiceImpl s= (SelServiceImpl)selStuMapper;
      /*  s.getCropNameList("apple");*/
        s.getCropList("apple",1);


    }

    /**
     * 测试根据列明获取的列
     */
    @Test
    public void databaseContent(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Object selStuMapper = context.getBean("selMapper");
        SelMapper s= (SelMapper)selStuMapper;
        Map m1 = new HashMap();
        m1.put("tableName","apple");
        m1.put("databaseName","crops");
        String s2 = s.selColumnName(m1);
        s2 = ChangFormat(s2);

        Map m2 = new HashMap();
        m2.put("tableName","apple");
        m2.put("colName",s2);
        m2.put("pageNumber","1");

        String apple = s.selCropData(m2);

        System.out.println(apple);
    }
    private static String ChangFormat(String s){
        StringBuffer ss = new StringBuffer();
        String[] split = s.split(",");
        int count = 0;
        for (String s1 : split) {
            String s3=null;
            if(count==0){
                s3 = "ifnull("+s1+",\"null\")";
                count++;
            }else{
                s3 = " ,\",\",ifnull("+s1+",\"null\")";
            }
            ss.append(s3);

        }

        return ss.toString();
    }
}
