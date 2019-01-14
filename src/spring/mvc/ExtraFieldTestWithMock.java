package spring.mvc;
//package spring;
//
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.aop.support.AopUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.TestContext;
//import org.springframework.test.context.TestExecutionListeners;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import com.miaozhen.babel.dao.metadata.ExtraFieldDao;
//import com.miaozhen.babel.dao.metadata.ExtraFieldRelationDao;
//import com.miaozhen.babel.exception.BusinessException;
//import com.miaozhen.babel.po.metadata.ExtraField;
//
///**
// * Mock + SpringTest 结合示例 <p>
// * 此示例中以service为主要测试对象，因此需要对service依赖的dao层对象进行mock（若dao层对象也依赖了其他对象则需要递归置换）
// * @title ExtraFieldDaoTest
// * @author lvzhaoyang
// * @date 2018年12月11日
// */
//@WebAppConfiguration
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({ "classpath:application-context.xml" })
//@TestExecutionListeners({MockDependencyInjectionTestExecutionListener.class})
//public class ExtraFieldTestWithMock {
//	
//	@Autowired
//	private ExtraFieldService service;
//	
//	@Mock
//	private ExtraFieldDao dao;
//	@Mock
//	private ExtraFieldRelationDao rdao;
//	
//	
//	@Before
//	public void setUp() {
//		// set stub data
////		MockitoAnnotations.initMocks(this);
//
//		long _id1 = 1L; // exist
//		long _id2 = 2L; // not exist
//		ExtraField ef1 = new ExtraField();
//		Mockito.when(dao.getByIdAndCheckExist(_id1)).thenReturn(ef1);
//		Mockito.when(dao.getByIdAndCheckExist(_id2)).thenReturn(null);
//		
//		Mockito.when(rdao.countByExtraFieldId(_id1)).thenReturn(1);
//		Mockito.doAnswer(invocationOnMock -> {
//			System.out.println("Call deleteWithCheck()");
//			return null;
//		}).when(dao).deleteWithCheck(Mockito.any());
//	}
//
//	
//	@Test(expected = BusinessException.class)
//	public void testServiceDelete() {
//		long id = 1L;
//		service.delete(id);
//	}
//
//
//}
//
///**
// * 测试启动注入依赖时，监听器负责将测试对象依赖的其他对象（递归的）置换为mock对象
// * @title MockDependencyInjectionTestExecutionListener
// */
//class MockDependencyInjectionTestExecutionListener extends DependencyInjectionTestExecutionListener {
//	private Map<Class<?>, Object> mockedObjects = new HashMap< > ( );
//	
//	@Override
//    protected void injectDependencies(final TestContext testContext) throws Exception {
//        super.injectDependencies(testContext);
//        setUpMock(testContext);
//    }
//
//
//	private void setUpMock(TestContext testContext) throws Exception {
//		Object bean = testContext.getTestInstance();
//		injectMock(bean);
//	}
//
//
//	private void injectMock(Object bean) throws Exception {
//		Field[] fields;
//		Class<?> targetClass;
//		if(AopUtils.isAopProxy(bean)) {
//			// jdk dynamic proxy
//			if(AopUtils.isJdkDynamicProxy(bean)) {
//				System.out.println("Proxy object:" + bean.getClass());
//				targetClass = AopUtils.getTargetClass(bean);
//				if(targetClass == null)
//					return; 
//				fields = targetClass.getDeclaredFields();
//				bean = ProxyUtils.getTarget(bean);
//			} else { // cglib proxy
//				return;
//			}
//		} else {
//			System.out.println("Common object:" + bean.getClass());
//			targetClass = bean.getClass();
//			fields = bean.getClass().getDeclaredFields();
//		}
//		
//		List<Field> injectedFields = new ArrayList<> ( );
//		for(Field field : fields) {
//			if(field.isAnnotationPresent(Mock.class)) {
//				 // 注入mock实例  
//                Object mockObj = Mockito.mock(field.getType());
//                mockedObjects.put(field.getType(), mockObj);
//                field.setAccessible(true);
//                field.set(bean, mockObj);
//			} else if(field.isAnnotationPresent(Autowired.class)) {
//				injectedFields.add(field);
//			}
//		}
//		
//		for(Field field : injectedFields) {
//			field.setAccessible(true);
//			Object object = field.get(bean);
//			if(!replaceInstance(field.getType(), object, bean, field)) {
//				injectMock(object);
//			}
//		}
//		
//	}
//
//
//	private boolean replaceInstance(Class<?> fieldType, Object fieldVal, Object bean, Field field) throws IllegalArgumentException, IllegalAccessException {
//		boolean replaced = false;
//		for(Map.Entry<Class<?>, Object> classObjEntry : mockedObjects.entrySet()) {
//			if(fieldType.isAssignableFrom(classObjEntry.getKey())) {
//				System.out.printf("Try to replace field '%s' in %s: old value [%s],  new value [%s]\n", 
//							field.getName(), bean.getClass().getName(), 
//							fieldVal.getClass().getName(), classObjEntry.getValue().getClass().getName());
//				// field has been set to avoid access control before calling this method
//				field.set(bean, classObjEntry.getValue());
//				replaced = true;
//				break;
//			}
//		}
//		return replaced;
//	}
//	
//}
