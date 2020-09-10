//package com.app.news;
//
//import java.util.List;
//
//import org.apache.ibatis.session.ExecutorType;
//import org.apache.ibatis.session.SqlSession;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.app.news.dao.TestDao;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@MapperScan("com.app.news.dao")
//public class TestApplication {
//
//	
//	@Autowired
//	SqlSessionTemplate sqlSessionTemplate;
//	@Autowired
//	TestDao testDao;
//	
//	@Test
//	public void testUpdateTaskStructure2(){
////		List<TaskstructureVO> list = taskstructureDao.selectAll();
////		
////		long begin = System.currentTimeMillis();
////		SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
////		TaskStructureDao dao = sqlSession.getMapper(TaskStructureDao.class);
////		if(null != list && list.size()>0){
////			for(TaskstructureVO vo :list){
////				vo.setHandler("ncc");
////				dao.update(vo);
////			}
////			sqlSession.commit();
////		}
////		System.out.println("=============>批量 update batch2耗时："+(System.currentTimeMillis()-begin));
//		
//	}
//}
