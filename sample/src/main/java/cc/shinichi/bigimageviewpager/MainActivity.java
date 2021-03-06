package cc.shinichi.bigimageviewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import cc.shinichi.library.ImagePreview;
import cc.shinichi.library.bean.ImageInfo;
import cc.shinichi.library.glide.ImageLoader;
import cc.shinichi.sherlockutillibrary.utility.ui.ToastUtil;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	String[] images = {
		"http://img3.16fan.com/live/origin/201805/21/E421b24c08446.jpg",
		"http://img3.16fan.com/live/origin/201805/21/4D7B35fdf082e.jpg",
		"http://img6.16fan.com/attachments/wenzhang/201805/18/152660818127263ge.jpeg", //  5760 * 3840
		"http://img3.16fan.com/live/origin/201805/21/2D02ebc5838e6.jpg",
		"http://img6.16fan.com/attachments/wenzhang/201805/18/152660818716180ge.jpeg", //  2280 * 22116
		"http://img3.16fan.com/live/origin/201805/21/14C5e483e7583.jpg",
		"http://img3.16fan.com/live/origin/201805/21/A1B17c5f59b78.jpg",
		"http://img3.16fan.com/live/origin/201805/21/94699b2be3cfa.jpg",
		"http://img6.16fan.com/attachments/wenzhang/201805/18/152660818716180ge.jpeg", //  2280 * 22116
		"http://img3.16fan.com/live/origin/201805/21/14C5e483e7583.jpg",
		"http://img3.16fan.com/live/origin/201805/21/EB298ce595dd2.jpg",
		"http://img6.16fan.com/attachments/wenzhang/201805/18/152660818127263ge.jpeg", //  5760 * 3840
		"http://img3.16fan.com/live/origin/201805/21/264Ba4860d469.jpg",
		"http://img6.16fan.com/attachments/wenzhang/201805/18/152660818716180ge.jpeg", //  2280 * 22116
		"http://img6.16fan.com/attachments/wenzhang/201805/18/152660818127263ge.jpeg" //  5760 * 3840
	};

	boolean enableDragClose = false;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		SwitchCompat switchDragClose = findViewById(R.id.switchDragClose);

		switchDragClose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				enableDragClose = isChecked;
			}
		});

		// 网络图片：
		ImageInfo imageInfo;
		final List<ImageInfo> imageInfoList = new ArrayList<>();
		for (String image : images) {
			imageInfo = new ImageInfo();
			// 原图地址
			imageInfo.setOriginUrl(image);
			// 缩略图，实际使用中，根据需求传入缩略图路径。如果没有缩略图url，可以将两项设置为一样。
			imageInfo.setThumbnailUrl(image.concat("-1200"));
			imageInfoList.add(imageInfo);
			imageInfo = null;
		}

		// 本地图片：将原图和缩略图地址传一样的即可。
		//ImageInfo imageInfo;
		//final List<ImageInfo> imageInfoList = new ArrayList<>();
		//for (String image : images) {
		//	imageInfo = new ImageInfo();
		//	imageInfo.setOriginUrl(image);
		//	imageInfo.setThumbnailUrl(image);
		//	imageInfoList.add(imageInfo);
		//	imageInfo = null;
		//}


		// 仅加载普清
		findViewById(R.id.buttonThumb).setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				ImagePreview
					.getInstance()
					.setContext(MainActivity.this)// 上下文
					.setIndex(0)// 从第一张图片开始，索引从0开始哦
					.setImageInfoList(imageInfoList)// 图片源
					.setShowDownButton(true)// 是否显示下载按钮
					.setLoadStrategy(ImagePreview.LoadStrategy.AlwaysThumb)// 加载策略，见下面介绍
					.setFolderName("BigImageViewDownload")// 保存的文件夹名称，SD卡根目录
					.setScaleLevel(1, 3, 8)// 设置三级缩放级别
					.setZoomTransitionDuration(300)// 缩放动画时长
					.setShowCloseButton(false)// 是否显示关闭页面按钮，在页面左下角
					.setEnableDragClose(enableDragClose)// 是否启用上拉/下拉关闭，默认不启用
					.setEnableClickClose(true)// 是否启用点击图片关闭，默认启用
					.start();
			}
		});

		// 仅加载原图
		findViewById(R.id.buttonOrigin).setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				ImagePreview
					.getInstance()
					.setContext(MainActivity.this)
					.setIndex(0)
					.setImageInfoList(imageInfoList)
					.setShowDownButton(true)
					.setLoadStrategy(ImagePreview.LoadStrategy.AlwaysOrigin)
					.setFolderName("BigImageViewDownload")
					.setScaleLevel(1, 3, 8)
					.setZoomTransitionDuration(300)
					.setShowCloseButton(true)
					.setEnableDragClose(enableDragClose)// 是否启用上拉/下拉关闭，默认不启用
					.setEnableClickClose(true)// 是否启用点击图片关闭，默认启用
					.start();
			}
		});

		// 手动模式：默认普清，手动高清
		findViewById(R.id.buttonDefault).setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				ImagePreview
					.getInstance()
					.setContext(MainActivity.this)
					.setIndex(0)
					.setImageInfoList(imageInfoList)
					.setShowDownButton(true)
					.setLoadStrategy(ImagePreview.LoadStrategy.Default)
					.setFolderName("BigImageViewDownload")
					.setScaleLevel(1, 3, 8)
					.setZoomTransitionDuration(500)
					.setShowCloseButton(true)
					.setEnableDragClose(enableDragClose)// 是否启用上拉/下拉关闭，默认不启用
					.setEnableClickClose(true)// 是否启用点击图片关闭，默认启用
					.start();
			}
		});

		// 网络自适应（WiFi原图，流量普清）
		findViewById(R.id.buttonAuto).setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				ImagePreview
					.getInstance()
					.setContext(MainActivity.this)
					.setIndex(0)
					.setImageInfoList(imageInfoList)
					.setShowDownButton(true)
					.setLoadStrategy(ImagePreview.LoadStrategy.NetworkAuto)
					.setFolderName("BigImageViewDownload")
					.setScaleLevel(1, 3, 5)
					.setZoomTransitionDuration(300)
					.setShowCloseButton(true)
					.setEnableDragClose(enableDragClose)// 是否启用上拉/下拉关闭，默认不启用
					.setEnableClickClose(true)// 是否启用点击图片关闭，默认启用
					.start();
			}
		});

		findViewById(R.id.buttonClean).setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				ImageLoader.cleanDiskCache(MainActivity.this);
				ToastUtil.getInstance()._short(MainActivity.this, "磁盘缓存已成功清除");
			}
		});
	}
}