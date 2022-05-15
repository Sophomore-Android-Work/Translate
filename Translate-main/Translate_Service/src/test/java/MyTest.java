import mapper.MydictMapper;
import mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import pojo.Mydict;
import pojo.User;
import utils.MyBatisUtil;

public class MyTest {


    @Test
    public void test1(){
        SqlSession sqlSession = MyBatisUtil.getSqlSession();

        MydictMapper mapper = sqlSession.getMapper(MydictMapper.class);

        Mydict apple = mapper.selectByEng("apple");
        System.out.println(apple);

        UserMapper mapper1 = sqlSession.getMapper(UserMapper.class);
        User user = mapper1.selectUserByAccount("181068");
        System.out.println(user);
    }

}
