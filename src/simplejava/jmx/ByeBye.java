package simplejava.jmx;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;



public class ByeBye extends NotificationBroadcasterSupport implements ByeByeMBean{

	static int seq = 0;
	
	@Override
	public void sayBye() {
		System.out.println("Bye ~");
		// send a notification to Hello.getName
		Notification notification = new Notification("getName", this, ++seq);
		super.sendNotification(notification);
		
	}

}
