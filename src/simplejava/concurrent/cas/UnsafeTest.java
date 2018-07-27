/**
 * 
 */
package simplejava.concurrent.cas;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

/**
 * @Title UnsafeTest
 * @Description
 * @Author lvzhaoyang
 * @Date 2018年4月25日
 */
public class UnsafeTest {

	/**
	 * @Description
	 * @Author lvzhaoyang
	 * @Date 2018年4月25日
	 */
	public static void main(String[] args) {
		Unsafe us = getUnsafe();
//		testArray(us);
		try {
			testObject(us);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void testObject(Unsafe us) throws Exception {
		//通过allocateInstance直接创建对象
        User user = (User) us.allocateInstance(User.class);

        Class userClass = user.getClass();
        Field name = userClass.getDeclaredField("name");
        Field age = userClass.getDeclaredField("age");
        Field id = userClass.getDeclaredField("id");

        //获取实例变量name和age在对象内存中的偏移量并设置值
        us.putInt(user, us.objectFieldOffset(age),18);
        us.putObject(user, us.objectFieldOffset(name),"android TV");

        // 这里返回 User.class，
        Object staticBase = us.staticFieldBase(id);
        System.out.println("staticBase:"+staticBase);

        //获取静态变量id的偏移量staticOffset
        long staticOffset = us.staticFieldOffset(id);
        //获取静态变量的值
        System.out.println("设置前的ID:"+us.getObject(staticBase,staticOffset));
        //设置值
        us.putObject(staticBase,staticOffset,"SSSSSSSS");
        //获取静态变量的值
        System.out.println("设置前的ID:"+us.getObject(staticBase,staticOffset));
        //输出USER
        System.out.println("输出USER:"+user.toString());

        long data = 1000;
        byte size = 1;//单位字节

        //调用allocateMemory分配内存,并获取内存地址memoryAddress
        long memoryAddress = us.allocateMemory(size);
        //直接往内存写入数据
        us.putAddress(memoryAddress, data);
        //获取指定内存地址的数据
        long addrData=us.getAddress(memoryAddress);
        System.out.println("addrData:"+addrData);
	}
	
	private static void testArray(Unsafe us) {
		int[] array = { 1, 2, 3 };
		long arrayAddr = us.arrayBaseOffset(array.getClass());
		int addrStep = us.arrayIndexScale(array.getClass());
		for(int i = 0; i != array.length; ++i) {
			long addr = arrayAddr + i * addrStep;
			System.out.println(us.getInt(array, addr));
		}
	}

	private static Unsafe getUnsafe() {
		Unsafe us = null;
		try {
			// 通过反射得到theUnsafe对应的Field对象
			Field field = Unsafe.class.getDeclaredField("theUnsafe");
			// 设置该Field为可访问
			field.setAccessible(true);
			// 通过Field得到该Field对应的具体对象，传入null是因为该Field为static的
			us = (Unsafe) field.get(null);
		} catch (Exception e) {
			us = null;
		}
		return us;
	}

}

class User{
    public User(){
        System.out.println("user 构造方法被调用");
    }
    private String name;
    private int age;
    private static String id="USER_ID";

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +'\'' +
                ", id=" + id +'\'' +
                '}';
    }
}