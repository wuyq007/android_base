class Config {

    public static appName = 'AndroidBase'
    public static applicationId = 'com.example.android.base'

    public static compileSdk = 34
    public static minSdk = 24
    public static targetSdk = 34
    public static versionCode = 1
    public static versionName = "1.0.0"


    /**
     * 各个模块名称*/
    public static modules = [libs  : [':libs:base-lib', ':libs:net-lib'],
                             common: [':common:view-common', ':common:glide-common', ':common:permissions-common'],
                             module: [''],

    ]

    /**
     * 组件版本*/
    private static versions = [paging_version   : '3.1.1',
                               nav_version      : '2.5.3',
                               room_version     : '2.5.0',
                               //targetSdk 33
                               lifecycle_version: '2.6.1',]


    /**
     * 网络框架*/
    public static square = [okhttp  : ['com.squareup.okhttp3:okhttp:4.12.0',
                                       'com.squareup.okhttp3:logging-interceptor:4.12.0',],
                            retrofit: ['com.squareup.retrofit2:retrofit:2.9.0',
                                       'com.squareup.retrofit2:converter-gson:2.9.0',
                                       'com.squareup.retrofit2:converter-scalars:2.9.0'],
                            okio    : 'com.squareup.okio:okio:3.7.0',]


    /**
     * 通用组件
     **/
    public static common = [


    ]

    public static commonView = [background: 'com.github.JavaNoober.BackgroundLibrary:libraryx:1.7.6']


}