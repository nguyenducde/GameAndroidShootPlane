package com.example.adeso1.huyenthoai.Player;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.adeso1.huyenthoai.Main.MainActivity;
import com.example.adeso1.huyenthoai.Player.Object.BigBang;
import com.example.adeso1.huyenthoai.Player.Object.Boss;
import com.example.adeso1.huyenthoai.Player.Object.Boss1;
import com.example.adeso1.huyenthoai.Player.Object.Boss2;
import com.example.adeso1.huyenthoai.Player.Object.Fight;
import com.example.adeso1.huyenthoai.Player.Object.Planes;
import com.example.adeso1.huyenthoai.Player.Object.Planes2;
import com.example.adeso1.huyenthoai.Player.Object.User;
import com.example.adeso1.huyenthoai.Player.work.Bullet;
import com.example.adeso1.huyenthoai.Player.work.BulletBossLevel1;
import com.example.adeso1.huyenthoai.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public  class  GameView extends SurfaceView implements Runnable{

    TextView txtDiem,txtChoiLai;
    ImageView imgHome;
        private  Thread thread;
        private int screenX,screenY,score=0;
        private User users;
        private Paint paint;
        private DatabaseReference mDatabase;
        public static float screenRatioX, screenRatioY;
        private  boolean isplaying;
        private  long time;
        private GameActivity activity;
        private Planes[] planes;
        private Planes2[]planes2s;
        private Random random;
        private Fight fight;
        private List<Bullet> bullets;
        private  List<BulletBossLevel1> bulletBossLevel1s;
        private Boss boss;
        private Boss1 boss1;
        private Boss2 boss2;

        private BigBang bigBang;

        private boolean pause=false;
        FirebaseUser user;

        private  boolean flagPlane=false;
    private boolean flagScrore=false;
        private  int flagBoss=7;
        private  int flagBoss2=1;
        private  int flagBoss1=1;
        public int flagplane=1;
        private  int flagbulletboss=1;

        private static Context context;
        //thanhhp
        public int HP=360;
        public  int HPBoss=500;
        public  int HPBoss1=500;
        public  int HPBoss2=500;
        //Game kết thúc
        private  boolean isgameover=false;
        private Background background1,background2;
        private Background2 background21,background22;
    final CountDownTimer  timer=new CountDownTimer(15000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            time=   millisUntilFinished/1000;
        }

        @Override
        public void onFinish() {
            flagPlane=true;
        flagBoss=0;
//        timer.start();
        }
    }.start();


        public GameView(GameActivity gameActivity,int screenX,int screenY) {

            super(gameActivity);
           this.activity=gameActivity;
            this.screenX=screenX;
            this.screenY=screenY;
            screenRatioX=1;
            screenRatioY=1;
            background1=new Background(screenX,screenY,getResources());
            background2=new Background(screenX,screenY,getResources());
            background2.y=-screenY;
            background21=new Background2(screenX,screenY,getResources());
            background22=new Background2(screenX,screenY,getResources());
            background22.y=-screenY;
            bigBang=new BigBang((getResources()));
            fight=new Fight(this,screenY,getResources());
            bullets =new ArrayList<>();
            bulletBossLevel1s=new ArrayList<>();
            paint = new Paint();
            planes=new Planes[4];
            for (int i=0;i<4;i++)
            {
                Planes plane=new Planes(getResources());
               planes[i]=plane;
            }
            planes2s=new Planes2[4];
            for (int i=0;i<4;i++)
            {
                Planes2 planes2=new Planes2(getResources());
                planes2s[i]=planes2;
            }

            //
            boss=new Boss(this,screenY,getResources());
            boss1=new Boss1(this,screenY,getResources());
            boss2=new Boss2(this,screenY,getResources());
            random=new Random();

        }

        @Override
        public void run() {
            while (isplaying){
                //Nếu thua thì thoát khỏi vòng lặp
                if(isgameover==true)
                {
                    break;
                }

                update();
                draw();
                sleep();
            }
            //Hiện cửa sổ khi thua
            if(isgameover==true)
            {
                activity.runOnUiThread(new Runnable() {
                    public void run() {


                        Dialog dialog=new Dialog(getContext());
                        dialog.setContentView(R.layout.dialog);
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialog.show();


                        //Given information to database Firebase
                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        user= FirebaseAuth.getInstance().getCurrentUser();
                        //if username and email != null
                        if (!user.getDisplayName().isEmpty()&&!user.getEmail().isEmpty()) {
                            // Name, email address, and profile photo Url
                            String name = user.getDisplayName();
                            String email = user.getEmail();
                            users=new User(name,email,score);
                            mDatabase.push().setValue(users);


                        }
                        if(user.getDisplayName().isEmpty()&&user.getEmail().isEmpty())
                        {
                            users=new User("Không xác định","Không xác định",score);
                            mDatabase.push().setValue(users);
                        }
                        //if name equal null
                        if(user.getDisplayName().isEmpty()&&!user.getEmail().isEmpty())
                        {
                            users=new User("Không xác định",user.getEmail(),score);
                            mDatabase.push().setValue(users);
                        }
                        //if email equal null
                        if(!user.getDisplayName().isEmpty()&&user.getEmail().isEmpty())
                        {
                            users=new User(user.getDisplayName(),"Không xác định",score);
                            mDatabase.push().setValue(users);
                        }


                        //
                        //
                        txtDiem=dialog.findViewById(R.id.tvDiem);
                        txtDiem.setText("Score: "+score);
                        dialog.findViewById(R.id.tvChoiLai).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent myIntent = new Intent(getContext(),GameActivity.class);
                                getContext().startActivity(myIntent);
                            }
                        });

                        dialog.findViewById(R.id.imgHome).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent myIntent = new Intent(getContext(), MainActivity.class);
                                getContext().startActivity(myIntent);
                            }
                        });



                    /*
                    Intent myIntent = new Intent(getContext(), MainActivity.class);
                    getContext().startActivity(myIntent);

                     */
                    }
                });
            }

        }
        private void update(){
            //Cho backgroud di chuyển từ trên xuống dưới
            setBackground();
            setBackground2();
            //chỉnh đạn
            setBullet();
            //Chỉnh máy bay địch
            setPlane();
            setPlanes2();
            //Thiết lập boss
            setBoss();
            setBoss2();
            setBoss1();
            //Chỉnh đạn boss

            setBulletBossLevel1s();

        }
        private  void draw(){


            if(getHolder().getSurface().isValid());
            {


               Canvas canvas=getHolder().lockCanvas();
                try {

                    if(canvas!=null)
                    {
                        //cho backgroud di chuyển từ trên xuống dưới
                        drawBackGroud(canvas);
                        drawBackGroud2(canvas);
                        //vẽ máy bay địch xuất hiện
                        drawPlane(canvas);
                        //vẽ máy bay mình đang bắn
                        drawMyPlane(canvas);
                        //Vẽ đạn
                        drawBullet( canvas);
                        //Vẽ điểm
                        drawScrore(canvas);
                        //Vẽ máu
                        drawBlood(canvas);
                        //vẽ boss

                        drawBoss(canvas);
                        //Vẽ đạn boss
                        drawBulletBossLV1(canvas);
                        drawBloodBoss(canvas);
                        drawBoss2(canvas);
                        drawBoss1(canvas);
                        drawplan2(canvas);
//
                    }

                } finally {
                    if(canvas!=null)
                    {
                        getHolder().unlockCanvasAndPost(canvas);
                    }
                }




            }

        }
        private void sleep(){
            try {
                thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public void resume(){
            isplaying =true;
            thread=new Thread(this);
            thread.start();

        }
        public  void pause(){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public boolean onTouchEvent( MotionEvent event) {
            float dX = (float) event.getX();
            float dY = (float) event.getY();
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:

                    break;
                case MotionEvent.ACTION_UP:

                    break;
                case MotionEvent.ACTION_MOVE:
                    fight.isGoing=true;

                    fight.x=dX;
                    fight.y=dY;

                    break;

            }
            return true;
        }
        public void newBullet() {
            Bullet bullet=new Bullet(getResources());;
            bullet.x = (float) (fight.x+screenX/52);
            bullet.y= (float) (fight.y-screenY/20);
            bullets.add(bullet);
        }

        public void newBulletBossLV1()
        {
            BulletBossLevel1 bullet=new BulletBossLevel1(getResources());;
            bullet.x = (float) (boss.x+screenX/5);
            bullet.y= (float) (boss.y+screenY/8);
            bulletBossLevel1s.add(bullet);
        }
        public  void setBackground()
        {
            background1.y+=15-screenRatioY;
            background2.y+=15-screenRatioY;

            if(background1.y+(-background1.background.getHeight())>0){
                background1.y=-screenY;
            }
            if(background2.y+(-background1.background.getHeight())>0){
                background2.y=-screenY;
            }

            if(fight.isGoing==false)
            {
                fight.x=screenX/3+40;
                fight.y=screenY-(screenY/4);
            }
        }
        public void setBackground2()
        {
            background21.y+=15-screenRatioY;
            background22.y+=15-screenRatioY;

            if(background21.y+(-background21.background.getHeight())>0){
                background21.y=-screenY;
            }
            if(background22.y+(-background21.background.getHeight())>0){
                background22.y=-screenY;
            }
        }
        public void setPlane()
        {   if(flagPlane==false) {
            //Lấy từng máy bay
            for (int i = 0; i < planes.length; i++) {

                planes[i].y += planes[i].speed;
                if (planes[i].width + planes[i].x > screenX) {
                    planes[i].y = 0;
                    planes[i].x = random.nextInt(screenX - planes[i].width);
                }

                if (planes[i].y + planes[i].height > screenY) {
                    int bound = (int) (30 * screenRatioX);
                    planes[i].speed = random.nextInt(bound);
                    if (planes[i].speed < 10 * screenRatioY) {
                        planes[i].speed = (int) (10 * screenRatioY);

                    }
                    //cho máy bay địch di chuyển từ trên xuống dưới theo chiều dọc
                    planes[i].y = 0;
                    planes[i].x = random.nextInt(screenX - planes[i].width);

                }
//                if (flagplane==1)
//                {
//                    flagplane=2;
//                    for (Planes2 planes2:planes2s)
//                    {
//                        planes2.x=0;
//                        if( planes2.x==0)
//                        {
//                            planes2.y=random.nextInt(screenY);
//                            if (planes2.x<=screenX/2)
//                                planes2.x+=10;
//                        }
//                }

                }


                /*
                if(i>=3)
                {
                    i=0;
                }
              if(Rect.intersects(planes[i].getShape(),planes[i+1].getShape()))
              {
                  planes[i+1].y = 0;
                  planes[i+1].x = random.nextInt(screenX - planes[i].width);
              }
              /*
                 */

                //Nếu máy bay dc tạo ra mà lớn hơn chiều ngang của màn hình


            }
        }


        public void setPlanes2()
        {
            for (Planes2 planes2:planes2s)
            {
                for (int i=0;i<planes2s.length;i++)
                {
                planes2.x-=1;
                if (planes2.x+planes2.width<0)
                {
//                    int bound =10;
//                    planes2.speed=random.nextInt(bound);
                    if(planes2.speed<10*screenRatioX)
                        planes2.speed=1;//(int)(1* screenRatioX)
                        planes2.x=screenX;

                    if (random.nextInt(screenY-planes2.height)<screenY/2)
                    {

                            planes2s[i].y=100;


                    }


                    if (planes2s[i].x==0)
                    {
                        planes2s[i].x=0;
                        planes2s[i].y-=10;
                    }


                }
                }
            }

        }
        public  void setBulletBossLevel1s()
        {
            List<BulletBossLevel1> trash=new ArrayList<>();

            for(BulletBossLevel1 bullet:bulletBossLevel1s)
            {
                if (bullet.y>screenY)
                    trash .add(bullet);
                if(flagbulletboss!=0)
                {
                    bullet.y+=100;
                    flagbulletboss=2;
                }
                if (flagbulletboss==2&&bullet.y>=screenY/2&& bullet.x>=screenX/3)
                {
                    bullet.x+=50;
                    bullet.y-=5000;
                    flagbulletboss=3;
                }
                if (flagbulletboss==3&&bullet.y<screenY/4)
                {
                    bullet.x-=20;
                }
                if (flagbulletboss==4)
                {
                    bullet.y-=100;
                    bullet.x+=20;


                }
//                if (flagbulletboss==3&&bullet.y>=screenY/3)
//                {
//                    bullet.y-=100;
//                    bullet.x+=30;
//                }

//                bullet.x -=10;




//                if(Rect.intersects(bullet.getShape(),fight.getShape()))
//                {
//                    if(HP<=100)
//                    {
//                        isgameover=true;
//                    }
//                    HP-=10;
//                }

            }
            //Tự hủy đạn
            for(BulletBossLevel1 bullet:trash)
            {
                bullets.remove(bullet);
            }
        }
        public void setBullet()
        {
            List<Bullet> trash=new ArrayList<>();

            for(Bullet bullet:bullets)
            {
                if (bullet.y>screenY)
                    trash .add(bullet);
                bullet.y-=300;
                //Xử lý va chạm với máy bay địch
                if (Rect.intersects(bullet.getShape(),boss.getShape()))
                {
                    HPBoss-=10;
                    if (HPBoss<=0)
                    {
//                        boss.x=50000;

                    }


                }
                for (Planes pla:planes)
                {
                    //Nếu tọa độ máy bay địch với tọa độ đạn giống nhau
                    if(Rect.intersects(pla.getShape(),bullet.getShape()))
                    {
                       // bullet.getShape();
                        pla.x=-500;
                        bullet.x=screenX+500;
                        score+=5;
                    }
                    //Nếu tọa độ máy bay mình với địch giống nhau
                    if(Rect.intersects(pla.getShape(),fight.getShape()))
                    {
                        //Nếu hết máu
                        if(HP<=100)
                        {
                         isgameover=true;
                        }
                        HP-=5;
                        pla.x=-500;
                    }
                }
            }
            //Tự hủy đạn
            for(Bullet bullet:trash)
            {
                bullets.remove(bullet);
            }
        }
        public  void setBoss(){
            if(flagBoss==0)
            {
                boss.x=screenX/4;
                boss.y=0;
                flagBoss=1;
            }
            if(flagBoss==1)
            {
                if(boss.y<=screenY/7) {
                    boss.x = screenX / 4;
                    boss.y += 3;
                }
                if(boss.y>=screenY/7)
                {
                    flagBoss=2;
                }

            }
            if(flagBoss==2)
            {
                boss.x -= 3;
                boss.y -= 3;

                if(boss.x<=screenX/30)
                {
                    flagBoss=3;
                }

            }
            if(flagBoss==3)
            {
                boss.x+=3;
                boss.y-=3;
                if(boss.x>=screenX/4)
                {
                    flagBoss=4;
                }
            }
            if(flagBoss==4)
            {
                boss.x+=3;
                boss.y+=3;
                if(boss.x>=screenY/4-20)
                {
                    flagBoss=5;
                }
            }
            if(flagBoss==5)
            {
                boss.x-=3;
                boss.y+=5;
                if(boss.y>=screenY/7)
                {
                    flagBoss=1;
                }
            }
        }
        public  void setBoss1()
        {
            if (flagBoss1==1)
            {
                boss1.x=screenX/4;
                boss1.y=screenY/40-500;
                flagBoss1++;

            }
            if (flagBoss1==2)
            {
                boss1.y+=10;
//                flagBoss1=3;
            }
            if(boss1.y>=screenY/5)
            {
                boss1.y-=10;
                boss1.x+=7;
//                flagBoss1++;
            }
            if (boss1.x==screenX)
            {
                boss1.x=screenX;

            }
            if( boss1.x==800)
            {
                boss1.y+=5;
                boss1.x-=10;
                flagBoss1=3;
            }
//            if(boss1.x==screenX-150;)
//            if (flagBoss1==3&&boss1.x>=screenX/4)
//            {
//                boss1.x=screenX/4;
//                boss1.y+=10;
//            }
        }
        public void setBoss2()
        {

            if (flagBoss2==1)
            {
                boss2.x=0;
                boss2.y=0;
                flagBoss2=2;
            }
            if (flagBoss2==2)
            {
                boss2.x+=5;
                flagBoss2=2;
            }
            if (boss2.x>=screenX/2)
            {
                boss2.x=screenX/2;
                boss2.y+=5;
                flagBoss2=3;
            }
            if (boss2.y>=screenY/2)
            {
                boss2.y=screenY/2;
                boss2.x-=5;
            }
            if(boss2.x==0)
            {
                boss2.x=0;
                boss2.y-=5;
                if (boss2.y<=0)
                {
                    flagBoss2=1;

                }

            }

        }
        public  void drawBackGroud(Canvas canvas )
        {
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);
        }
        public void drawBackGroud2(Canvas canvas)
        {
            if (HPBoss <= 0) {

                canvas.drawBitmap(background21.background, background21.x, background21.y, paint);
                canvas.drawBitmap(background22.background, background22.x, background22.y, paint);

            }

        }
        public void drawPlane(Canvas canvas)
        {
            if(flagPlane==false) {
                for (Planes pla : planes) {
                    canvas.drawBitmap(pla.getPlanes(), pla.x, pla.y, paint);
                }
            }
        }
        public void drawMyPlane(Canvas canvas)
        {
            canvas.drawBitmap(fight.getFight(),fight.x,fight.y,paint);
        }
        public void drawBullet(Canvas canvas)
        {
            for(Bullet bullet:bullets)
            {
                canvas.drawBitmap(bullet.bullet,bullet.x+screenX/46-1,bullet.y+screenX/35,paint);
                canvas.drawBitmap(bullet.bullet,bullet.x+screenX/9-3,bullet.y+screenX/35,paint);

            }
        }
        public void drawBulletBossLV1(Canvas canvas)
        {

//            for(BulletBossLevel1 bullet:bulletBossLevel1s)
//            {
//                canvas.drawBitmap(bullet.bullet,screenX/3,screenY/3,paint);
////                if (time==5)
////                {
////
////                }
//
////                try {
////                    canvas.drawBitmap(bullet.bullet,bullet.x,bullet.y,paint);
////                    Thread.sleep(50);
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
//                canvas.drawBitmap(bullet.bullet,bullet.x,bullet.y,paint);
//
//
//
//
//            }
        }
        public void drawScrore(Canvas canvas)
        {if(flagScrore==false)
        {
            paint.setColor(Color.WHITE);
            paint.setTextSize(40);
            canvas.drawText("Score:"+score + "",screenX-screenX/6,73,paint);
        }

            if(score>=95)
            {flagScrore=true;
                paint.setColor(Color.WHITE);
                paint.setTextSize(40);
                canvas.drawText("Score:"+score + "",screenX-screenX/5,70,paint);
            }
        }
        public  void drawBlood(Canvas canvas)
            {   Paint paintLineBlood=new Paint();


                    paintLineBlood.setColor(Color.GREEN);
                    paintLineBlood.setStrokeWidth(32);
                    paintLineBlood.setTextSize(35);
                //paint.setStrokeCap(ROUND);
                    canvas.drawText("HP",5,63,paintLineBlood);
                    canvas.drawLine(100,50,HP,50,paintLineBlood);
                Paint paintBlood=new Paint();
                paintBlood.setStyle(Paint.Style.STROKE);
                paintBlood.setColor(Color.GREEN);
                canvas.drawRect(100,33,screenX/3,66,paintBlood);


            }
        public  void drawBoss(Canvas canvas)
        {

            if (flagBoss != 7) {
                canvas.drawBitmap(boss.getPlanes(), boss.x, boss.y, paint);
            }

            if (HPBoss<=0)
            {   canvas.drawBitmap(bigBang.getBigBang(),boss.x+80,boss.y+260,paint);
                canvas.drawBitmap(bigBang.getBigBang(),boss.x+200,boss.y+210,paint);
                canvas.drawBitmap(bigBang.getBigBang(),boss.x+350,boss.y+280,paint);
//                canvas.drawBitmap(bigBang.getBigBang(),boss.x+250,boss.y+300,paint);
            }

//

        }
        public void drawBoss1(Canvas canvas)
        {
//            canvas.drawBitmap(boss1.getPlanes(),boss1.x,boss1.y,paint);
        }
        public  void drawBoss2(Canvas canvas)
        {
//            if (HPBoss<=0)
//            {
//                canvas.drawBitmap(boss2.getPlanes(),boss2.x,boss2.y,paint);
//            }
        }
        public  void drawBloodBoss(Canvas canvas)
        {
                        Paint paintLineBlood = new Paint();
                        paintLineBlood.setColor(Color.RED);
                        paintLineBlood.setStrokeWidth(25);
                        paintLineBlood.setTextSize(35);

                        canvas.drawLine(boss.x + 50, boss.y + 90, boss.x + HPBoss, boss.y + 90, paintLineBlood);
                        //nãy tọa độ bắt đầu của y nhỏ hơn cái kết thúc y nên nó mới bị lich phải để 2 cái đó bằng nhau
                        //mày nhìn thấy cái startX hk cái đó tọa đọ bất đầu
                        //oke
                        //draw frames
                        Paint paintBlood = new Paint();
                        paintBlood.setStyle(Paint.Style.STROKE);//rong
                        paintBlood.setColor(Color.RED);

                        canvas.drawRect(boss.x + 50, boss.y + 75, boss.x + 500, boss.y + 100, paintBlood);
//////                   f

        }
        public void drawplan2(Canvas canvas)
        {
            for (Planes2 planes2:planes2s)
            {
                canvas.drawBitmap(planes2.getPlanes(),planes2.x,planes2.y,paint);
               for (Bullet bullet:bullets)
               {
                   canvas.drawBitmap(bullet.bullet,planes2.x,planes2.y,paint);
               }
            }

        }

    }

