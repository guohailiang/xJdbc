import java.sql.*;

/**/
public class JdbcDemo {

    public static void executeSql(Connection con, String sql){
        PreparedStatement psql;
        try {
            psql = con.prepareStatement(sql);
            psql.executeUpdate();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //声明Connection对象
        Connection con;
        //加载jdbc软件包后加载驱动程序
        String driver = "com.mysql.cj.jdbc.Driver";
        //URL指向要访问的数据库名mydata,并且配置ssl和timezone信息，采用&符号连接
        String url = "jdbc:mysql://localhost:3306/jdbctest?useSSL=false&serverTimezone=UTC";
        //MySQL配置时的用户名
        String user = "root";
        //MySQL配置时的密码
        String password = "123456";
        //遍历查询结果集
        try {
            //加载驱动程序
            Class.forName(driver);
            //1.getConnection()方法，连接MySQL数据库！！
            con = DriverManager.getConnection(url,user,password);
            if(!con.isClosed())
                System.out.println("Succeeded connecting to the Database!");
            //2.创建statement类对象，用来执行SQL语句！！
            Statement statement = con.createStatement();
            //要执行的SQL语句
            String sql = "select * from emp";
            //3.ResultSet类，用来存放获取的结果集！！
            ResultSet rs = statement.executeQuery(sql);
            System.out.println("-----------------");
            System.out.println("执行结果如下所示:");
            System.out.println("-----------------");
            System.out.println("姓名" + "\t" + "职称");
            System.out.println("-----------------");

            String job = null;
            String id = null;
            while(rs.next()){
                //获取stuname这列数据
                job = rs.getString("job");
                //获取stuid这列数据
                id = rs.getString("ename");

                //输出结果
                System.out.println(id + "\t" + job);
            }

            /*插入数据*/
            executeSql(con,"insert into emp (empno,ename,job,hiredate,sal) " +
                    "value(3234,'wanggang','manager','2012-10-09',2000.0)");

            /*更新数据*/
            executeSql(con,"update emp set sal=2100.0 where empno=3234");

            /*删除数据*/
            executeSql(con,"delete from emp where empno=3233");

            rs.close();
            con.close();
        } catch(ClassNotFoundException e) {
            //数据库驱动类异常处理
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        } catch(SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
            System.out.println("数据库数据成功获取！！");
        }






    }

}