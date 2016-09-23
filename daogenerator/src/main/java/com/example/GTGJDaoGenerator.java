package com.example;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Schema;

public class GTGJDaoGenerator {

    public static void main(String[] args) throws Exception{
        // 创建了一个用于添加实体（Entity）的模式（Schema）对象
        // 构造方法第一个参数为数据库版本号
        // 第二个参数为自动生成的实体类将要存放的位置,前面为我的Android Module的包名
        Schema schema = new Schema(1,"com.lzh.administrator.androidframe.GreenDAo");
//        当然，如果你愿意，你也可以分别指定生成的 Bean 与 DAO 类所在的目录，只要如下所示：
//      Schema schema = new Schema(1, "com.lzh.administrator.androidframe.GreenDAo.bean");
//      schema.setDefaultJavaPackageDao("com.lzh.administrator.androidframe.GreenDAo.dao");

        // 模式（Schema）同时也拥有两个默认的 flags，分别用来标示 entity 是否是 activie 以及是否使用 keep sections。
        // schema2.enableActiveEntitiesByDefault();
        // schema2.enableKeepSectionsByDefault();

        // 一旦你拥有了一个 Schema 对象后，你便可以使用它添加实体（Entities）了。
        addNote(schema);

        // 最后我们将使用 DAOGenerator 类的 generateAll() 方法自动生成代码，此处你需要根据自己的情况更改输出目录（既之前创建的 java-gen)。
        // 其实，输出目录的路径可以在 build.gradle 中设置，有兴趣的朋友可以自行搜索，这里就不再详解。
        new DaoGenerator().generateAll(schema,"app/src/main/java-gen");
    }

    private static void addNote(Schema schema) {
        // 一个实体（类）就关联到数据库中的一张表
        //指定需要生成实体类的类名,类名确定了那么表名也是根据这个类名来自动命名的,例如下面这个,生成的表名叫做station_bean
        Entity stationBean = schema.addEntity("StationBean");
        // 你也可以重新给表命名
        // note.setTableName("NODE");

        //添加字段
        stationBean.addIdProperty();
        stationBean.addStringProperty("name");
        stationBean.addStringProperty("pinyin");
        stationBean.addStringProperty("pinyins");
        stationBean.addStringProperty("stationcode");
        stationBean.addStringProperty("firstpys");
    }
}

