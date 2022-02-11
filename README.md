# SideBar[![](https://jitpack.io/v/cyh120190774/SideBar.svg)](https://jitpack.io/#cyh120190774/SideBar)


A-Z 侧边导航栏  

![效果](./images/4nja6-1n69p.gif))

## Gradle

``` groovy
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
``` groovy
dependencies {
   implementation 'com.github.cyh120190774:SideBar:1.00'
}
```
## Usage

**XML**

``` xml 
        <com.cyh.sidebarview.SideBar
            android:id="@+id/sb_index"
            android:layout_width="30dp"
            android:layout_height="match_parent"
            android:layout_alignParentRight="true"
            android:background="@color/white"
            app:isShowDialog="true"/>
```

**Java**

``` java
        //SideBar滑动 RecyclerView跟随滑动
        binding.sbIndex.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                if (adapter.getData().size() < 2) {
                    return;
                }
                if (s.equals(binding.sbIndex.getLetters()[0])) {
                    manager.scrollToPositionWithOffset(0, 0);
                } else if (s.equals(binding.sbIndex.getLetters()[binding.sbIndex.getLetters().length - 1])) {
                    manager.scrollToPositionWithOffset(
                            adapter.getData().size() - 1,
                            0
                    );
                } else {
                    for (int i = 0; i < mBeans.size(); i++) {
                        if (mBeans.get(i).fisrtSpell.compareToIgnoreCase(s) == 0) {
                            manager.scrollToPositionWithOffset(i, 0);
                            break;
                        }
                    }
                }


            }
        });

       //RecyclerView滑动 SideBar跟随位置
        binding.rvArea.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.getLayoutManager() instanceof LinearLayoutManager){
                    int pos = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                    String str =  ((PhoneAreaAdapter)binding.rvArea.getAdapter()).getData().get(pos).getFisrtSpell();
                    binding.sbIndex.setChooseStr(str);
                }
            }
        });
```





**属性**

``` xml  
   <declare-styleable name="SideBar">

        <attr name="sideTextArray" format="reference" /> <!--sidebar的字符列表-->

        <attr name="sideTextColor" format="color" /> <!--sidebar的字符颜色-->

        <attr name="sideTextSelectColor" format="color" /> <!--sidebar的字符选中时的颜色-->

        <attr name="sideTextSize" format="dimension" /> <!--sidebar的字符大小-->

        <attr name="sideBackground" format="reference|color" /> <!--sidebar的背景颜色-->
        
        <attr name="isShowDialog" format = "boolean" /><!--是否显示弹窗-->

        <attr name="dialogTextColor" format="color" /> <!--选中弹窗字符颜色-->

        <attr name="dialogTextSize" format="dimension" /> <!--选中弹窗字符大小-->

        <attr name="dialogTextBackground" format="reference|color" /> <!--选中弹窗字符背景颜色-->

        <attr name="dialogTextBackgroundWidth" format="dimension" /> <!--选中弹窗字符背景宽度-->

        <attr name="dialogTextBackgroundHeight" format="dimension" /> <!--选中弹窗字符背景高度-->

    </declare-styleable>

```