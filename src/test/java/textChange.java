import com.bjh.service.impl.SelServiceImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class textChange {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Object selStuMapper = context.getBean("selServiceImpl");
        SelServiceImpl s= (SelServiceImpl)selStuMapper;
        /*  s.getCropNameList("apple");*/

    }
}
