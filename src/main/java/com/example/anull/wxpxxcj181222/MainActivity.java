package com.example.anull.wxpxxcj181222;



import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Paint;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.AudioManager;
import android.media.Image;
import android.media.ImageReader;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.LitePal;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static com.example.anull.wxpxxcj181222.PermisionUtils.verifyStoragePermissions;
//import static com.example.wjyaw.weixianpincaiji.PermisionUtils.verifyStoragePermissions;

/**
 * use 自定义相机Camera2
 * author 孙尚磊
 * create time 2017-4-25
 */
public class MainActivity extends AppCompatActivity {


//---------------------
//    作者：lizhq888
//    来源：CSDN
//    原文：https://blog.csdn.net/lizhq888/article/details/79907953
//    版权声明：本文为博主原创文章，转载请附上博文链接！
//


    //用litepal实现了 不需要了
//    //数据库
//    private MyDatabaseHelper dbHelper;

    //设置时间日期
    String type = "";
    String where = "";
    String mode = "";
    String banzu = "";
    String banzu1 = "";
    String name = "";
    String name1 = "";


    long time = System.currentTimeMillis();
    Date date = new Date(time);
    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    public String nowtime = format.format(date);


    //初始化循刷新页面---------------------------
    public static final int msgKey1 = 1;
    //-------------------------------------------

    private TextureView tv;
    private Button btn;
    private String mCameraId = "0";//摄像头id（通常0代表后置摄像头，1代表前置摄像头）
    private final int RESULT_CODE_CAMERA = 1;//判断是否有拍照权限的标识码
    private CameraDevice cameraDevice;
    private CameraCaptureSession mPreviewSession;
    private CaptureRequest.Builder mCaptureRequestBuilder, captureRequestBuilder;
    private CaptureRequest mCaptureRequest;
    private ImageReader imageReader;
    private int height = 0, width = 0;
    private Size previewSize;
    private ImageView iv;
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();

    //--------------------------------------------------------------------------
    //相机咔嚓声音的参数
    private SoundPool sp;//声明一个SoundPool
    private int music;//创建某个声音对应的音频ID
    //--------------------------------------------------------------------------


    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //锁定竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//-----------------------------------------
        //运行时权限时调用
        verifyStoragePermissions(this);
//---------------------------------------------------------
        //初始化数据库
        //创建数据路
        LitePal.getDatabase();
        //添加数据
        if (LitePal.findFirst(Data1.class) == null) {
            Chushihua chushihua = new Chushihua();
            chushihua.RenyuanChushihua();
        }
//____________________________________________________


//          用LitePal实现了 不需要了
//----------————————————————————————————————————————————————————————
//        //创建数据库
//        dbHelper=new MyDatabaseHelper(this,"wxpjc.db",null,1);
//        //执行建表语句
//        dbHelper.getWritableDatabase();
//————————————————————————————————————————————————————————————————


        //隐藏标题
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        //刷新页面线程1111111111111111111111111111111111
        //刷新页面线程
        new TimeThread().start();


        tv = (TextureView) findViewById(R.id.tv);
        btn = (Button) findViewById(R.id.btn);
        iv = (ImageView) findViewById(R.id.iv);

        //相机声音-----------------------------------------
        sp = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        music = sp.load(this, R.raw.yinxiao, 1);
        //--------------------------------------------------

        //拍照
        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                sp.play(music, 1, 1, 0, 0, 1);//咔嚓声
                takePicture();
            }
        });
        //设置TextureView监听
        tv.setSurfaceTextureListener(surfaceTextureListener);


        //setting按键

        Button button = (Button) findViewById(R.id.setting);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, setting.class);
                startActivity(intent);
            }
        });

    }

//
//1------------------------------------------绑定按键-------------------------------
//
    //发现人员名单 按键 弹出选择人员  一级菜单

//    List<Data2>date2s= LitePal.findAll(Data2.class);
//    for(int i=0;i<date2s.size();i++){}

    //Object[] areas = date2s.toArray();


    public void onClick_Event(View v) {
        final String[] areas = Buttonfuzhi.buttons1();                                             //setGravity(Gravity.CENTER)
        new AlertDialog.Builder(MainActivity.this)./*setTitle("几班发现的？").*/setItems(areas, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int nextId) {
                //Toast.makeText(MainActivity.this, "您已经选择了: " + nextId + ":" + areas[nextId],Toast.LENGTH_LONG).show();
                List<Data1> data1s = LitePal.where("id=?", String.valueOf(nextId)).find(Data1.class);
                //String[] returns = new String[data1s.size()];
                //returns[0]=data1s.get(0).toString();
                banzu = areas[nextId];

                onClick_Event1(nextId + 1);
                dialog.dismiss();
            }
        }).show();
    }

    //二级菜单


    public void onClick_Event1(int Data1Id) {
        final String[] areas1 = Buttonfuzhi.buttons2(Data1Id);
        new AlertDialog.Builder(MainActivity.this)./*setTitle("谁发现的？").*/setItems(areas1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Toast.makeText(MainActivity.this, "您已经选择了: " + which + ":" + areas[which],Toast.LENGTH_LONG).show();
                name = areas1[which];
                onClick_Event2();
                dialog.dismiss();
            }
        }).show();
    }

    public void onClick_Event2() {
        final String[] areas1 = {"值机", "手检"};
        new AlertDialog.Builder(MainActivity.this)/*.setTitle("值机还是手检？")*/.setItems(areas1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Toast.makeText(MainActivity.this, "您已经选择了: " + which + ":" + areas[which],Toast.LENGTH_LONG).show();
                mode = areas1[which];
                dialog.dismiss();
            }
        }).show();
    }

    //处置人员名单
    //一级
    //private String[] areas2 = new String[]{"一班", "二班","三班", "四班", "自己和", "关于", "取消"};

    public void onClick_Event2(View v) {
        final String[] areas = Buttonfuzhi.buttons1();
        new AlertDialog.Builder(MainActivity.this)/*.setTitle("处置的是几班的？")*/.setItems(areas, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Toast.makeText(MainActivity.this, "您已经选择了: " + which + ":" + areas[which],Toast.LENGTH_LONG).show();
                banzu1 = areas[which];
                onClick_Event21(which + 1);
                dialog.dismiss();
            }
        }).show();
    }

    //e二级
    //private String[] areas21 = new String[]{"谁谁", "谁谁谁", "让他理", "是的法规理", "自己和", "关于", "取消"};

    public void onClick_Event21(int Data1Id) {
        final String[] areas1 = Buttonfuzhi.buttons2(Data1Id);
        new AlertDialog.Builder(MainActivity.this)./*setTitle("谁是处置？").*/setItems(areas1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Toast.makeText(MainActivity.this, "您已经选择了: " + which + ":" + areas[which],Toast.LENGTH_LONG).show();
                name1 = areas1[which];
                dialog.dismiss();
            }
        }).show();
    }

    //物品类型
    //private String[] areas3 = new String[]{"限制携带物品", "禁止携带物品"};
    public void onClick_Event3(View v) {
        final String[] areas3 = Buttonfuzhi.buttons3();
        new AlertDialog.Builder(MainActivity.this)./*setTitle("危险品类型").*/setItems(areas3, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Toast.makeText(MainActivity.this, "您已经选择了: " + which + ":" + areas[which],Toast.LENGTH_LONG).show();
                type = areas3[which];
                onClick_Event4();
                dialog.dismiss();
            }
        }).show();
    }

    //处理方式
    //private String[] areas4 = new String[]{"安检收缴", "旅客自弃", "旅客带回"};

    public void onClick_Event4() {
        final String[] areas4 = Buttonfuzhi.buttons4();
        new AlertDialog.Builder(MainActivity.this)./*setTitle("危险品整哪去了").*/setItems(areas4, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Toast.makeText(MainActivity.this, "您已经选择了: " + which + ":" + areas[which],Toast.LENGTH_LONG).show();
                where = areas4[which];
                dialog.dismiss();
            }
        }).show();
    }


    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case msgKey1:
                    long time = System.currentTimeMillis();
                    Date date = new Date(time);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd   HH:mm:ss");
                    //初始化姓名
                    TextView textView00 = (TextView) findViewById(R.id.zhaoxiang_date);
                    textView00.setText(nowtime);
                    TextView textView = (TextView) findViewById(R.id.zhaoxiang_name);
                    textView.setText("安检员:"+banzu + " " + name + "  " + mode);
                    TextView textView0 = (TextView) findViewById(R.id.zhaoxiang_name1);
                    textView0.setText("处置员:"+banzu1 + " " + name1);
                    TextView textView1 = (TextView) findViewById(R.id.zhaoxiang_type);
                    // 往布局上写时间和危险品去向和类型
                    //nowtime = format.format(date);
                    textView1.setText(type + "     " + where);
                    break;
                default:
                    break;
            }
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onPause() {
        super.onPause();
        if (cameraDevice != null) {
            stopCamera();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume() {
        super.onResume();
        startCamera();
    }

    /**
     * TextureView的监听
     */
    private TextureView.SurfaceTextureListener surfaceTextureListener = new TextureView.SurfaceTextureListener() {

        //可用
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            MainActivity.this.width = width;
            MainActivity.this.height = height;
            openCamera();
        }


        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        //释放
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            stopCamera();
            return true;
        }

        //更新
        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };


    /**
     * 打开摄像头
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void openCamera() {
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        //设置摄像头特性
        setCameraCharacteristics(manager);
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                //提示用户开户权限
                String[] perms = {"android.permission.CAMERA"};
                ActivityCompat.requestPermissions(MainActivity.this, perms, RESULT_CODE_CAMERA);

            } else {
                manager.openCamera(mCameraId, stateCallback, null);
            }

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置摄像头的参数
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setCameraCharacteristics(CameraManager manager) {
        try {
            // 获取指定摄像头的特性
            CameraCharacteristics characteristics
                    = manager.getCameraCharacteristics(mCameraId);
            // 获取摄像头支持的配置属性
            StreamConfigurationMap map = characteristics.get(
                    CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            // 获取摄像头支持的最大尺寸
            Size largest = Collections.max(
                    Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)), new CompareSizesByArea());
            // 创建一个ImageReader对象，用于获取摄像头的图像数据
            imageReader = ImageReader.newInstance(largest.getWidth(), largest.getHeight(),
                    ImageFormat.JPEG, 2);
            //设置获取图片的监听
            imageReader.setOnImageAvailableListener(imageAvailableListener, null);
            // 获取最佳的预览尺寸
            previewSize = chooseOptimalSize(map.getOutputSizes(
                    SurfaceTexture.class), width, height, largest);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static Size chooseOptimalSize(Size[] choices
            , int width, int height, Size aspectRatio) {
        // 收集摄像头支持的大过预览Surface的分辨率
        List<Size> bigEnough = new ArrayList<>();
        int w = aspectRatio.getWidth();
        int h = aspectRatio.getHeight();
        for (Size option : choices) {
            if (option.getHeight() == option.getWidth() * h / w &&
                    option.getWidth() >= width && option.getHeight() >= height) {
                bigEnough.add(option);
            }
        }
        // 如果找到多个预览尺寸，获取其中面积最小的
        if (bigEnough.size() > 0) {
            return Collections.min(bigEnough, new CompareSizesByArea());
        } else {
            //没有合适的预览尺寸
            return choices[0];

        }
    }


    // 为Size定义一个比较器Comparator
    static class CompareSizesByArea implements Comparator<Size> {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public int compare(Size lhs, Size rhs) {
            // 强转为long保证不会发生溢出
            return Long.signum((long) lhs.getWidth() * lhs.getHeight() -
                    (long) rhs.getWidth() * rhs.getHeight());
        }
    }


    /**
     * 摄像头状态的监听
     */
    private CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        // 摄像头被打开时触发该方法
        @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
        @Override
        public void onOpened(CameraDevice cameraDevice) {
            MainActivity.this.cameraDevice = cameraDevice;
            // 开始预览
            takePreview();
        }

        // 摄像头断开连接时触发该方法
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onDisconnected(CameraDevice cameraDevice) {
            MainActivity.this.cameraDevice.close();
            MainActivity.this.cameraDevice = null;

        }

        // 打开摄像头出现错误时触发该方法
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onError(CameraDevice cameraDevice, int error) {
            cameraDevice.close();
        }
    };

    /**
     * 开始预览
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    private void takePreview() {
        SurfaceTexture mSurfaceTexture = tv.getSurfaceTexture();
        //设置TextureView的缓冲区大小
        mSurfaceTexture.setDefaultBufferSize(previewSize.getWidth() + 1, previewSize.getHeight() + 1);
        //获取Surface显示预览数据
        Surface mSurface = new Surface(mSurfaceTexture);
        try {
            //创建预览请求
            mCaptureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            // 设置自动对焦模式
            mCaptureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            //设置Surface作为预览数据的显示界面
            mCaptureRequestBuilder.addTarget(mSurface);
            //创建相机捕获会话，第一个参数是捕获数据的输出Surface列表，第二个参数是CameraCaptureSession的状态回调接口，当它创建好后会回调onConfigured方法，第三个参数用来确定Callback在哪个线程执行，为null的话就在当前线程执行
            cameraDevice.createCaptureSession(Arrays.asList(mSurface, imageReader.getSurface()), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(CameraCaptureSession session) {
                    try {
                        //开始预览
                        mCaptureRequest = mCaptureRequestBuilder.build();
                        mPreviewSession = session;
                        //设置反复捕获数据的请求，这样预览界面就会一直有数据显示
                        mPreviewSession.setRepeatingRequest(mCaptureRequest, null, null);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onConfigureFailed(CameraCaptureSession session) {

                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }


    /**
     * 拍照
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void takePicture() {
        try {
            if (cameraDevice == null) {
                return;
            }
            // 创建拍照请求
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            // 设置自动对焦模式
            captureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            // 将imageReader的surface设为目标
            captureRequestBuilder.addTarget(imageReader.getSurface());
            // 获取设备方向
            int rotation = getWindowManager().getDefaultDisplay().getRotation();
            // 根据设备方向计算设置照片的方向
            captureRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION
                    , ORIENTATIONS.get(rotation));
            // 停止连续取景
            mPreviewSession.stopRepeating();
            //拍照
            CaptureRequest captureRequest = captureRequestBuilder.build();
            //设置拍照监听
            mPreviewSession.capture(captureRequest, captureCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听拍照结果
     */
    private CameraCaptureSession.CaptureCallback captureCallback = new CameraCaptureSession.CaptureCallback() {
        // 拍照成功
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
            // 重设自动对焦模式
            captureRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_CANCEL);
            // 设置自动曝光模式
            captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
            try {
                //重新进行预览
                mPreviewSession.setRepeatingRequest(mCaptureRequest, null, null);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onCaptureFailed(CameraCaptureSession session, CaptureRequest request, CaptureFailure failure) {
            super.onCaptureFailed(session, request, failure);
        }
    };

    /**
     * 监听拍照的图片
     */
    private ImageReader.OnImageAvailableListener imageAvailableListener = new ImageReader.OnImageAvailableListener() {
        // 当照片数据可用时激发该方法
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onImageAvailable(ImageReader reader) {

            //先验证手机是否有sdcard
            String status = Environment.getExternalStorageState();
            if (!status.equals(Environment.MEDIA_MOUNTED)) {
                Toast.makeText(getApplicationContext(), "你的sd卡不可用。", Toast.LENGTH_SHORT).show();
                return;
            }
            // 获取捕获的照片数据
            Image image = reader.acquireNextImage();
            ByteBuffer buffer = image.getPlanes()[0].getBuffer();
            byte[] data = new byte[buffer.remaining()];
            buffer.get(data);

            //----------------------------------------------
            //获取jpg格式的照片用来增加水印
            Bitmap bitmap1 = BitmapFactory.decodeByteArray(data, 0, data.length);

            //自己定义的方法 nowtime和 危险品去向 危险品类型合并

            Bitmap bitmap2 = createWatermark(bitmap1, nowtime + " " + type + " " + where, "安检员:"+banzu + " " + name + " " + mode+"    处置员:"+banzu1+" "+name1);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] datas = baos.toByteArray();

            //-----------------------------------------------


            //手机拍照都是存到这个路径
            String filePath = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/";
            String picturePath = System.currentTimeMillis() + ".jpg";
            File file = new File(filePath, picturePath);
            try {
                //存到本地相册
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(datas);
                fileOutputStream.close();
                //强制刷新图库  否则只在文件管理中显示 图库找不到
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(file.getPath()))));

                //显示图片
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                Bitmap bitmap = BitmapFactory.decodeByteArray(datas, 0, datas.length, options);
                iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                iv.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                image.close();
            }
        }


    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
        switch (permsRequestCode) {
            case RESULT_CODE_CAMERA:
                boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (cameraAccepted) {
                    //授权成功之后，调用系统相机进行拍照操作等
                    openCamera();
                } else {
                    //用户授权拒绝之后，友情提示一下就可以了
                    Toast.makeText(MainActivity.this, "请开启应用拍照权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 启动拍照
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void startCamera() {
        if (tv.isAvailable()) {
            if (cameraDevice == null) {
                openCamera();
            }
        } else {
            tv.setSurfaceTextureListener(surfaceTextureListener);
        }
    }

    /**
     * 停止拍照释放资源
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void stopCamera() {
        if (cameraDevice != null) {
            cameraDevice.close();
            cameraDevice = null;
        }

    }


    //        Toast ts = Toast.makeText(this,"欢迎你的光临!", Toast.LENGTH_LONG);
//                 ts.show() ;//这个是打开的意思,就是调用的意思。


    //给图片加水印
    private Bitmap createWatermark(Bitmap bitmap, String date, String name) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint p = new Paint();
        // 水印颜色
        p.setColor(Color.parseColor("#00ffff"));
        // 水印字体大小
        p.setTextSize(80);
        //抗锯齿
        p.setAntiAlias(true);
        //绘制图像
        canvas.drawBitmap(bitmap, 0, 0, p);
        //绘制文字
        p.setColor(Color.parseColor("#00ffff"));
        canvas.drawText(date, 150, 150, p);
        p.setTextSize(80);
        canvas.drawText(name, 150, 350, p);
        //.save(Canvas.ALL_SAVE_FLAG);
        canvas.save();
        canvas.restore();
        return bmp;
    }

    //最上边的字幕连续刷新
    public class TimeThread extends Thread {
        @Override
        public void run() {
            super.run();
            do {
                try {
                    Thread.sleep(500);
                    Message msg = new Message();
                    msg.what = msgKey1;
                    mHandler.sendMessage(msg);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
    }
}