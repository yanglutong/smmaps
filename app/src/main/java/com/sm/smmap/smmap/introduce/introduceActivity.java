package com.sm.smmap.smmap.introduce;

import android.annotation.SuppressLint;
import android.graphics.Color;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.sm.smmap.smmap.R;

/**
 * 关于公司介绍
 */
public class introduceActivity extends FragmentActivity {

    private TextView content1, content2, content3, content4, content5, content6,phone,email,web;
    private ImageView images;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
//        setStatBar();
        findvies();
        setData();
    }
    @SuppressLint("NewApi")
    public void setStatBar() {//根据版本设置沉浸式状态栏
        View decorView = getWindow().getDecorView();
        int option =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        getWindow().setStatusBarColor(Color.TRANSPARENT
        );
    }

    private void setData() {
        String s1 = "（1）公司概况\n" +
                "河北三明通信股份有限公司成立于2013年，实缴资本3006万元，其位于石家庄市桥西区西城国际B座18楼整层，产权自有，总面积1498.85平方米，其中研发涉密区660平方米；是一家从事移动通信技术和拉曼光谱技术的研发、生产、销售和服务的高新技术企业。\n" +
                "2019年3月21日河北三明通信股份有限公司（股票代码为：873238）在“全国中小企业股份转让系统”成功上市，并于2019年4月16日在北京“全国中小企业股份转让系统”正式挂牌，为河北企业的发展赢得了荣誉。\n" +
                " “新三板”的成功挂牌，意味着三明股份正式迈入资本市场，对增加企业知名度，扩大营销、吸引人才等具有积极而深远的意义，同时为新形势下中小企业拓宽融资渠道、促进良好发展树立了成功典范。对已取得的成绩，三明股份将此“清零”——开始“新的长征”，以本次挂牌为新的起点，乘势而上、整合资源、规范运作，充分利用资本市场促进企业发展，在不久的将来做强做大。\n";
        String s2 = "资质证书     公司已取得了ISO9001质量管理体系认证证书、ISO4001环境管理体系认证证书、软件企业认定证书、高新技术企业证书、河北省科技型中小企业证书；公司领导十分重视保密工作，严格遵守公安部门工作纪律，遵守秘密、保护秘密、严守秘密，公司已取得了军工三级保密资格单位证书、河北省安全技术防范一级证书、安全技术防范一级备案证。";
        String s3 = "（3）公司团队\n" +
                "公司部门分工明确，人员结构合理，是一支优秀的、充满活力的专业队伍。公司现设有研发事业部、技质部、市场部、综合部、财务部、系统集成部，现有员工60余人。公司管理团队经验丰富，为公司的运作提供了良好的支持；技术团队由博士、硕士作为主要技术骨干，多人获得高中级项目经理、网络工程师、安全技术防范工程资格证书，对未来移动通信技术具有独到的见解及成熟的运作经验。\n";
        String s4 = "marginBottom=\"20dp\"\n" +
                "                android:text=\"（4）成立实验室\n" +
                "公司与河北省公安厅技侦总队、大唐移动通信成立了“河北省公安厅移动通信技侦专业实验室”。目前实验室已研发的产品均取得了“软件产品登记证”和“软件著作权登记证书”。 自主研发的特种通信产品已列入公安部技侦装备列装目录，并在国内公安、检察院以及军队等众多领域得到全面推广，业务覆盖全国。\n";
        String s5 = "（5）业务能力\n" +
                "公司自成立以来，立足于服务公安技侦一线工作，连续3年为北戴河暑期安保工作提供技术保障。随时响应各地技侦部门的技术研讨、技能培训、实战协助等工作，并配合全国技侦部门侦办大要案件200多起，受到了多地技侦单位好评";
        String s6 = "（6）企业愿景\n" +
                "我公司将在公安部和河北省公安厅的指导下，继续本着“诚实为本，守信至上”的经营理念，以“科技推动发展，创新打造永恒”的发展思路，专注于公安特种通信产品领域的创新发展，加大研发投入、扩大生产规模，完善售后服务保障措施；凭借不断增强的企业应用化创新能力和定制化服务能力，以赢得更多用户的信任和支持，全力为各行业各领域用户提供高品质服务保障。\n";
        String cc = ToSBC(s1);
//        content1.setText("        "+cc);
    }

    private void findvies() {
        content1 = findViewById(R.id.content1);
        content2 = findViewById(R.id.content2);
        content3 = findViewById(R.id.content3);
        content4 = findViewById(R.id.content4);
        content5 = findViewById(R.id.content5);
        content6 = findViewById(R.id.content6);
        findViewById(R.id.iv_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        phone,email,web,images
//        phone = findViewById(R.id.phone);
//        email = findViewById(R.id.email);
//        web = findViewById(R.id.web);
//        images = findViewById(R.id.images);
    }



    public static String ToSBC(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == ' ') {
                c[i] = '\u3000';
            } else if (c[i] < '\177') {
                c[i] = (char) (c[i] + 65248);
            }
        }
        return new String(c);


    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
