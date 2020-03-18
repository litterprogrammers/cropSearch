public class textChange {
    public static void main(String[] args) {
//        String s = ChangFormat("统一编号,品种名称,译名,原产地,保存单位,科名,属名,学名,高程,东经,北纬,生育期D,生育期评价,始果年龄,早果性评价,果实成熟期,成熟期评价,单果重G,单果重评价,果形,色泽,外观评价,肉质,汁液,风味,内质评价,可溶固形物,可溶性糖,可滴定酸,维生素C,特异及用途,省,样品类型,图象,叶色");
//        System.out.println(s);
        getDatabaseName("jdbc:mysql://localhost:3306/crops?useSSL=false&serverTimezone=UTC");
    }

    public static String getDatabaseName(String s){
        //jdbc:mysql://localhost:3306/crops?useSSL=false&serverTimezone=UTC
        int i = s.indexOf("?");

        s = s.substring(0,i);
        int i1 = s.lastIndexOf("/");
        s = s.substring(i1+1);
        return s;
    }
    public static String ChangFormat(String s){
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
