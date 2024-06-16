import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicInteger;
import java.awt.Graphics;

public abstract class Actor implements Runnable {
  private volatile boolean mRunning;
  private static int mThreadId = 0;
  private Thread mActorThread;
  static int size = Cell.size;
  private Cell loc;
  enum Player {Human, Bot};
  private Player player;
  int speed;
  int turns;
  int damage;
  int score;
  protected MoveStrategy strat;
  protected String desc;
  protected BufferedImage img;

  public Actor(Cell l, BufferedImage i, String d, Player p, int s, int harm) {
    loc = l;
    img = i;
    player = p;
    speed = s;
    desc = d;
    strat = new RandomMove();
    turns = speed;
    damage = harm;
    mRunning = false;
    //this.start();
    mActorThread = new Thread(this);
  }

  public void paint(Graphics g) {
    //start();
    g.drawImage(img, loc.x, loc.y, size, size, null);
  }

  public boolean isHuman() {
    return player == Player.Human;
  }

  public boolean isBot() {
    return player == Player.Bot;
  }

  public void setLocation(Cell inLoc) {
    loc = inLoc;
  }

  public Cell getLocation() {
    return loc;
  }

  public void run() {
    while(mRunning) {
      try {
        Thread.sleep(15);
      } catch (Exception e) {
        // TODO: handle exception
      }
    }
  }

  public boolean isRunning() {
    return mRunning;
  }

  public void start() {
    if(mRunning) {
      return;
    }
    //mActorThread = new Thread(this);
    mActorThread.start();
    mActorThread.setName(desc);
    mRunning = true;
    mThreadId++;
    
    System.out.println(desc+","+loc.col+","+loc.row+"id: "+mActorThread.getId());

  }
  public void stop() {
		if (!mRunning) {
			return;
		}
		mRunning = false;
		mActorThread.interrupt();
		try {
			mActorThread.join();
		} catch (InterruptedException eExn) {
			// TODO Auto-generated catch block
			eExn.printStackTrace();
		}
	}
}
