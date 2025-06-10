<template>
  <h2>五、机器学习决策树</h2>
  <a-collapse default-active-key = "0" :bordered="false">
    <a-collapse-panel header = "1. 实验背景与实验目的">
      <div class="content">
        <h3>
          1.1 实验背景
        </h3>
        <p>
          传统的决策树算法是基于不同事件发生的概率生成的，而事件的概率是通过蒙特卡洛法（或其他方法）获得的。
          但是随着机器学习的发展，基于历史数据统计的决策树也逐步完善，预测的准确率不断提升，当我们拥有大量的历史风险评估数据集时，机器学习决策树可以用统计代替概率使得结果更加可靠。
          因此我们在此处引入机器学习决策树，尝试利用新兴技术解决传统问题。
        </p>
        <p>
          机器学习决策树相较于传统决策树有两大区别：
        </p>
        <ul>
          <li>
            选择依据不同:
            <p>
              传统决策树的选择依据是当前结点的概率以及其子节点的数值计算期望值；而机器学习决策树是根据数据集中的某一个特征对数据集进行处理，选择其中最有特点的特征将数据集不断划分至不同的类别或完成回归任务。
            </p>
          </li>
          <li>
            工作原理不同:
            <p>
              传统决策树是对输入的不同方案进行计算，以损益值为依据，比较不同方案的期望损益值（简称期望值），决定方案的取舍，获得最优方案；而机器学习决策树在风险评估领域的用法是将历史数据划分为不同的等级（如A级项目、B级项目、C级项目等）并构建决策树模型，当遇到新方案时，可以根据模型预测方案的分级，从而进行初步的筛选。
            </p>
          </li>
        </ul>
      </div>
      <div class="content">
        <h3>
          1.2 实验目的
        </h3>
        <ul>
          <li>
            学习并理解机器学习决策树的基本原理；
          </li>
          <li>
            学习了解分类决策树的两种不同算法；
          </li>
          <li>
            使用sklearn库中的决策树算法，通过调整参数对比不同情况下两种算法的优劣。
          </li>
        </ul>
      </div>
    </a-collapse-panel>
    <a-collapse-panel header = "2. 机器学习决策树介绍">
      <div class="content">
        <h3>
          2.1 机器学习决策树定义
        </h3>
        <p>
          决策树(Decision Tree,又称为判定树)算法是机器学习中常见的一类算法，是一种以树结构形式表达的预测分析模型,属于监督学习（Supervised learning）的范畴。
          最早的的决策树算法是由Hunt等人于1966年提出，Hunt算法是许多决策树算法的基础，包括ID3、C4.5和CART等。
        </p>
        <p>
          决策树算法模拟人类在面临决策时的思考过程，通过一系列的决策节点和分支来对数据进行分类或预测。
          决策树的基本结构类似于一棵倒置的树，从根节点开始，通过一系列的内部节点沿着分支向下，最终到达叶节点。
          每个内部节点表示一个特征属性的测试，每个分支代表测试结果的一个可能取值，而每个叶节点表示一个类别标签或者回归值。
          假设根据大量数据（含 3 个指标：天气、温度、风速）构建了一棵“可预测学校会不会举办运动会”的决策树（如下图所示）。
        </p>
        <div class="imageContainer">
          <img src="../assets/example.png" alt="" style="width: 400px">
        </div>
        <br>
        <p>
          决策树的构建过程主要包括特征选择、树的生成和剪枝三个步骤：
        </p>
        <ul>
          <li>
            特征选择：根据某个准则（如信息增益、基尼不纯度等），选择最佳的特征作为当前节点的划分属性；
          </li>
          <li>
            树的生成：递归地将数据集划分为子集，直到数据集中的所有样本属于同一类别或满足停止条件；
          </li>
          <li>
            剪枝：为了避免过拟合，可以对生成的树进行剪枝操作，去除一些不必要的节点或分支。
          </li>
        </ul>

      </div>
      <div class="content">
        <h3>
          2.2 决策树分类
        </h3>
        <p>
          （1）分类树 - 这些被视为用于基于响应变量将数据集分成不同类的默认种类的决策树。 这些通常在响应变量本质上是分类时使用。
        </p>
        <p>
          （2）回归树 - 当响应或目标变量是连续或数字时，使用回归树。 与分类相比，这些通常用于预测类型的问题。
        </p>
      </div>
      <div class="content">
        <h3>
          2.3 特征选择方式
        </h3>
        <p>
          决策树学习的关键在于：如何选择最优划分属性。
          一般而言，随着划分过程的不断进行，我们自然希望决策树各分支结点所包含的样本尽可能属于同一类别，即结点的 “纯度” (purity) 越来越高。
        </p>
        <p>
          下面介绍几类较为主流的评选算法：
        </p>
        <ul>
          <li>
            信息增益（ID3算法选用的评估标准）；
          </li>
          <li>
            信息增益率（C4.5算法选用的评估标准）；
          </li>
          <li>
            基尼系数（ CART 算法选用的评估标准）。
          </li>
        </ul>
      </div>
      <div class="content">
        <h3>
          2.4 剪枝
        </h3>
        <p>
          对于决策树而言，当你不断向下划分，以构建一棵足够大的决策树时（直到所有叶子结点熵值均为 0），理论上就能将近乎所有数据全部区分开。所以，决策树的过拟合风险非常大。为此，需要对其进行剪枝处理。
        </p>
        <p>
          常用的剪枝策略主要有两个：
        </p>
        <ul>
          <li>
            预剪枝：构建决策树的同时进行剪枝处理（更常用）；
          </li>
          <li>
            后剪枝：构建决策树后再进行剪枝处理。
          </li>
        </ul>
      </div>
      <div class="content">
        <h3>
          2.5 应用场景
        </h3>
        <p>
          （1）决策树是流行的机器学习算法之一，它在财务中对期权定价有很大的用处。
        </p>
        <p>
          （2）遥感是基于决策树的模式识别的应用领域。
        </p>
        <p>
          （3）银行使用决策树算法按贷款申请人违约付款的概率对其进行分类。
        </p>
        <p>
          （4）Gerber产品公司，一个流行的婴儿产品公司，使用决策树机器学习算法来决定他们是否应继续使用塑料PVC（聚氯乙烯）在他们的产品。
        </p>
        <p>
          （5）Rush大学医学中心开发了一个名为Guardian的工具，它使用决策树机器学习算法来识别有风险的患者和疾病趋势。
        </p>
      </div>
    </a-collapse-panel>
    <a-collapse-panel header = "3. 决策树算法介绍">
      <div class="content">
        <p>
          最常见的三种决策树算法为ID3、C4.5以及CART，请阅读下面材料，留意不同决策树算法的适用情况、有什么特点、解决了什么问题。
        </p>
        <a-collapse default-active-key = "0" :bordered="false">
          <a-collapse-panel header = "ID3决策树">
            <h4>
              (1) 算法概述
            </h4>
            <p>
              ID3决策树是另一种非常重要的用来处理分类问题的结构，它形似一个嵌套N层的IF…ELSE结构，但是它的判断标准不再是一个关系表达式，而是对应的模块的信息增益。
              它通过信息增益的大小，从根节点开始，选择一个分支，如同进入一个IF结构的statement，通过属性值的取值不同进入新的IF结构的statement，直到到达叶子节点，找到它所属的“分类”标签。

            </p>
            <p>
              它的流程图是一课无法保证平衡的多叉树，每一个父节点都是一个判断模块，通过判断，当前的向量会进入它的某一个子节点中，这个子节点是判断模块或者终止模块（叶子节点），当且仅当这个向量到达叶子节点，它也就找到了它的“分类”标签。
            </p>
            <h4>
              (2) 划分标准
            </h4>
            <p>
              信息增益是ID3决策树划分数据集的根本，而信息增益是基于香农熵的一个概念。
            </p>
            <p>
              熵指的是一个系统“内在的混乱程度”，在这里也就是代表信息的“有序程度”。
              熵增的方向就是信息混乱度越大的方向，熵减的方向就是信息趋于“有序”的方向，所以说我们要划分数据集来使得数据集局部愈发趋于“有序化”。
              之所以是说数据集局部，是因为ID3在进行数据集划分的时候，选择一个使信息增益最大，即熵减最多的特征进行划分，而后该属性在后续的划分中将不再被考虑，所以这是一个递归的过程，也是一个不断局部化数据集的过程。
            </p>
            <p>
              【信息增益】(Information Gain)
            </p>
            <p>
              对于某一种划分的信息增益可以表示为“期望信息 - 该种划分的香农熵”。
              它的公式可以表示为：IG(T)=H(C)-H(C|T)。
              其中C代表的是分类或者聚类C，T代表的是则是当前选择进行划分的特征。
              这条公式表示了：选择特征T进行划分，则其信息增益为数据集的期望信息减去选择该特征T进行划分后的期望信息。
              这里要明确的是：期望信息就是香农熵。熵是信息的期望，所以熵的表示应该为所有信息出现的概率和其期望的总和，即：
            </p>
            <div class="imageContainer">
              <img src="../assets/InformationGain.png" alt="" style="width: 250px">
            </div>
            <h4>
              (3) 缺陷
            </h4>
            <p>
              ID3决策树的两个巨大缺陷：
            </p>
            <ul>
              <li>它将产生过度匹配问题：
                <p>
                  这里在不讨论信息增益的前提下，有这样一个例子：人的属性中有性别和年龄两个属性，由于人的性别只有男和女两种，年龄有很多种分支，当它有超过两个分支的时候，在用信息增益选择新的属性的时候，会选择年龄而不是性别，因为ID3决策树在使用信息增益来划分数据集的时候会倾向于选择属性分支更多的一个；
                </p>
              </li>
              <li>
                另外一个缺陷是：
                <p>
                  人的年龄假定为1~100，如果不进行离散化，即区间的划分，那么在选择年龄这个属性的时候，这棵决策树会产生最多100个分支，这是非常可怕而且浪费空间和效率的;
                  同时考虑这样一种情况：两个人的其他所有属性完全相同，他们的分类都是"A"，然而在年龄这一个树节点中分支了，而这个年龄下有一个跟这两个人很像，却不属于“A”类别的人，由于ID3决策树无法处理连续性数据，那么这两个人很有可能被划分到两个分类中，这是不合理的，这也是之后C4.5决策树考虑的问题。
                </p>
              </li>
            </ul>
          </a-collapse-panel>
          <a-collapse-panel header = "C4.5决策树">
            <h4>
              (1) 算法概述
            </h4>
            <p>
              C4.5决策树是ID3决策树的改进算法，它解决了ID3决策树无法处理连续型数据的问题以及ID3决策树在使用信息增益划分数据集的时候倾向于选择属性分支更多的属性的问题。
              它的大部分流程和ID3决策树是相同的或者相似的。
            </p>
            <p>
              C4.5决策树和ID3决策树相同，也可以产生一个离线的“决策树”，而且对于连续属性组成的C4.5决策树数据集，C4.5算法可以避开“测试集中的取值不存在于训练集”这种情况，所以不需要像ID3决策树那样，预先将测试集中不存在于训练集中的属性的取值，“手动地”加入到决策树中的问题。
              但是对于同时有离散属性和连续属性的数据集，离散属性部分仍旧是需要进行将存在于测试集，不存在于训练集中的取值（注意，是取值不是向量！）给删除或者加入到树的构造过程中。
            </p>
            <p>
              流行的C4.5决策树构造算法是没有进行修正的过程的，这会导致一个很严重的问题：C4.5决策树在构造树的时候倾向于选择连续属性作为最佳分割属性。
              所以C4.5需要一个修正的过程，在进行连续属性的信息增益率的计算的时候，要进行修正。
            </p>
            <h4>
              (2) 划分标准
            </h4>
            <p>
              C4.5使用的是信息增益率来划分“最好的”属性，这个“信息增益率”和ID3决策树使用的“信息增益”有什么区别呢？
            </p>
            <p>
              【信息增益率/信息增益比】（GainRatio）
            </p>
            <p>
              在选择决策树中某个结点上的分支属性时，假设该结点上的数据集为DataSet，其中包含Feature个描述属性，
              样本总数为len(DataSet)或者DataSet.shape[0]，设描述属性feature(不同于Feature，Feature是属性的个数，取值为DataSet.shape[1]，feature是某一个具体的属性)
              总共有M个不同的取值，则利用描述属性feature可以将数据集DataSet划分为M个子集，
              设这些子集为{DataSet1，DataSet2，…，DataSetN，…，DataSetM}，并且这些子集中样本在同一个子集中对应的feature属性的取值应该是相同的
              （若feature属性为离散属性，则取值为某一个离散值，若为连续属性，则取值为<=num，>num之一），
              用{len1，len2，…，lenN，…，lenM}表示每个对应的子集的样本的数量，则用描述属性feature来划分给定的数据集DataSet所得到的信息增益率/信息增益比为：
            </p>
            <div class="imageContainer">
              <img src="../assets/GainRatio.png" alt="">
            </div>
            <p>
              Gain(feature)的算法和上面ID3决策树计算信息增益的算法是一样的，事实上，求GainRatio的过程就是在上述的计算信息增益的过程中加上一个对其“稀释”的作用，使得取值多的feature不会占据主导地位。
            </p>
            <h4>
              (3)缺陷
            </h4>
            <ul>
              <li>
                计算效率不高：
                <p>
                  C4.5使用的信息增益率计算涉及熵的对数计算，特别是当属性值数量大时，计算成本较高。
                </p>
              </li>
              <li>
                处理连续数值属性不够高效：
                <p>
                  ID3算法只能处理离散属性，需要预先对连续属性进行离散化处理。C4.5虽然也能处理连续属性，但效率不高。
                </p>
              </li>
            </ul>
          </a-collapse-panel>
          <a-collapse-panel header = "CART决策树">
            <h4>
              (1) 方法概述
            </h4>
            <p>
              分类与回归树（classification and regression tree，CART）是决策树算法中的一种，与其他决策树算法相同，同样由特征选择，树的生成与剪枝组成。
            </p>
            <p>
              CART算法的重要基础包含以下三个方面：
            </p>
            <ul>
              <li>
                二分(Binary Split)：
                <p>
                  CART算法采用一种二分递归分割的技术，算法总是将当前样本集分割为两个子样本集，使得生成的决策树的每个非叶结点都只有两个分枝，CART二分每个特征（包括标签特征以及连续特征），如果标签特征有3个属性，可以将其中的两个属性归为一类，另一个属性归为一类。
                  因此CART算法生成的决策树是结构简洁的二叉树。因此CART算法适用于样本特征的取值为是或非的场景，对于连续特征的处理则与C4.5算法相似。
                </p>
              </li>
              <li>
                单变量分割(Split Based on One Variable)：
                <p>
                  每次最优划分都是针对单个变量。
                </p>
              </li>
              <li>
                剪枝策略(CART算法的关键点，也是整个Tree-Based算法的关键步骤。)：
                <p>
                  剪枝过程特别重要，所以在最优决策树生成过程中占有重要地位。
                  有研究表明，剪枝过程的重要性要比树生成过程更为重要，对于不同的划分标准生成的最大树(Maximum Tree)，在剪枝之后都能够保留最重要的属性划分，差别不大。反而是剪枝方法对于最优树的生成更为关键。
                </p>
              </li>
            </ul>
            <h4>
              (2) 划分标准
            </h4>
            <p>
              分类树用基尼指数最小化准则进行特征选择生成二叉树，基尼指数G(D,A)表示经A=a分割后集合D的不确定性，基尼指数越大，样本集合的不确定性就越大。
            </p>
            <p>
              【基尼系数】（Gini Index）
            </p>
            <p>
              对于一份label为分类变量的训练集，存在一个指标叫做基尼指数，其定义如下：
            </p>
            <div class="imageContainer">
              <img src="../assets/Gini.png" alt="">
            </div>
            <p>
              其中，pi表示某一类别的样本数占总样本数的比例。gini越小，表示样本分布越均匀（0的时候就表示只有一类了），越大越不均匀。
            </p>
            <p>
              【Gini增益】（GiniGain）
            </p>
            <p>
              Gini增益表示当前属性的一个混乱度。其中，Ni/N 表示当前类别占所有类别的概率。
            </p>
            <div class="imageContainer">
              <img src="../assets/GiniGain.png" alt="">
            </div>
            <p>
              最终Cart分类树选择GiniGain最小的特征作为划分特征，即对于每个特征（这里是A）的每个可能的值（这里是a）进行二分，每分一次就会产生两份样本，然后按照上式求每一次的综合基尼指数，最后选择基尼指数最小的对数据集进行分割。
            </p>
            <div class="imageContainer">
              <img src="../assets/GiniExample.png" alt="">
              <br>
              <img src="../assets/GiniExample2.png" alt="">
            </div>
            <h4>
              (3) 缺陷
            </h4>
            <ul>
              <li>无论是ID3, C4.5还是CART,在做特征选择的时候都是选择最优的一个特征来做分类决策：
                <p>
                  大多数，分类决策不应该是由某一个特征决定的，而是应该由一组特征决定的。这样绝息到的决策树更加准确。这个决策树叫做多变量决策树(multi-variate decision tree)。
                  在选择最优特征的时候，多变量决策树不是选择某一个最优特征，而是选择最优的一个特征线性组合来做决策。
                </p>
              </li>
              <li>
                如果样本发生一点点的改动，就会导致树结构的剧烈改变：
                <p>
                  可以通过集成学习里面的随机森林之类的方法解决
                </p>
              </li>
            </ul>
          </a-collapse-panel>
        </a-collapse>
      </div>
    </a-collapse-panel>
    <a-collapse-panel header="4. 决策树算法分析">
      <a-collapse default-active-key = '0' :bordered="false">
        <a-collapse-panel header = "4.1 决策树原理分析">
          <p>
            请仔细阅读上述材料，对比分析三种不同决策树的区别，填写下表：
          </p>
          <a-table :columns="columns" :dataSource="DecisionTreeStore.compareTableAnswer" rowKey="key" :pagination="false" bordered>
            <template #bodyCell="{ column, index }">
              <td>
                <template v-if="column.dataIndex === 'method'">
                  <a-select v-model:value="DecisionTreeStore.compareTableAnswer[index][column.dataIndex]" :options="methodOptions" style="width: 120px" />
                </template>
                <template v-else-if="column.dataIndex === 'continuous' || column.dataIndex === 'missing'">
                  <a-select v-model:value="DecisionTreeStore.compareTableAnswer[index][column.dataIndex]" :options="supportOptions" style="width: 120px" />
                </template>
                <template v-else>
                  <!-- 第一列：固定值 -->
                  {{ DecisionTreeStore.compareTableAnswer[index][column.dataIndex] }}
                </template>
              </td>
            </template>
          </a-table>
        </a-collapse-panel>
        <a-collapse-panel header = "4.2 决策树可视化介绍">
          <div>
            <h4>
              （1） 实验数据集介绍
            </h4>
            <a-descriptions bordered>
              <a-descriptions-item label="数据集名称">
                葡萄干数据集
              </a-descriptions-item>
              <a-descriptions-item label="数据集大小">
                450/450/900
              </a-descriptions-item>
              <a-descriptions-item label="类别">
                1.）Kecimen<br>
                2.）Besni
              </a-descriptions-item>
              <a-descriptions-item label="维度信息">
                1.）面积：给出葡萄干边界内的像素数。<br>
                2.）周长：它通过计算葡萄干边界与其周围像素之间的距离来测量环境。<br>
                3.）主轴长度：给出主轴的长度，主轴是葡萄干上可以画的最长线。<br>
                4.）小轴长度：给出小轴的长度，这是葡萄干上可以绘制的最短线。<br>
                5.）偏心率：它给出了椭圆偏心率的度量，椭圆具有与葡萄干相同的力矩。<br>
                6.）凸面积：给出葡萄干形成的区域最小凸壳的像素数。<br>
                7.）范围：给出葡萄干形成的区域与边界框中总像素的比率。<br>
              </a-descriptions-item>
            </a-descriptions>
          </div>
          <br>
          <div>
            <h4>
              （2） 实验函数参数介绍
            </h4>
            <a-descriptions bordered>
              <a-descriptions-item label="决策树类型">
                C4.5：以entropy为划分依据；<br>
                CART：以gini为划分依据；
              </a-descriptions-item>
              <a-descriptions-item label="划分标准">
                best：特征的所有划分点中找出最优的划分点;<br>
                random：随机的在部分划分点中找局部最优的划分点。<br>
                默认的‘best’适合样本量不大的时候，而如果样本数据量非常大，此时决策树构建推荐‘random’。
              </a-descriptions-item>
              <a-descriptions-item label="训练集划分">
                验证集占总数据集的比例
              </a-descriptions-item>
              <a-descriptions-item label="最大深度">
                决策树的最大深度。
                一般数据比较少或者特征少的时候可以不用管这个值，如果模型样本数量多，特征也多时，推荐限制这个最大深度，具体取值取决于数据的分布。
                常用的可以取值10-100之间，常用来解决过拟合。
              </a-descriptions-item>
            </a-descriptions>
          </div>
          <br>
        </a-collapse-panel>
        <a-collapse-panel header = "4.3 决策树可视化分析">
          <div>
            <p>
              请选择不同参数，依据数据集进行不少于十组的决策树构建，总结并分析算法规律。
            </p>
            <br>
            <a-form>
              <a-form-item label="请选择决策树类型">
                <a-radio-group v-model:value="tree_type"  button-style="solid">
                  <a-radio-button value="C4.5">
                    C4.5决策树
                  </a-radio-button>
                  <a-radio-button value="CART">
                    CART决策树
                  </a-radio-button>
                </a-radio-group>
              </a-form-item>
              <a-form-item label="请选择特征划分标准">
                <a-radio-group v-model:value="tree_splitter"  button-style="solid">
                  <a-radio-button value="best">
                    Best
                  </a-radio-button>
                  <a-radio-button value="random">
                    Random
                  </a-radio-button>
                </a-radio-group>
              </a-form-item>
              <a-form-item label="请输入最大深度(1-100)">
                <a-input-number
                  v-model:value="tree_max_depth"
                  :min="1"
                  :max="100"
                  :step="1"
                  :placeholder="'请输入..'"
                  @blur="handleBlur(0)"
                />
              </a-form-item>
              <a-form-item label="请输入数据集划分比例（验证集/总数）（0.1-0.8）">
                <a-input-number
                  v-model:value="tree_proportion"
                  :min="0.1"
                  :max="0.8"
                  :step="0.05"
                  :placeholder="'请输入..'"
                  @blur="handleBlur(1)"
                />
              </a-form-item>
              <a-form-item>
                <a-button @click="generateTree">
                  生成决策树
                </a-button>
              </a-form-item>
            </a-form>
            <div v-if="treeResult.imageUrl">
              <a-descriptions title="决策树信息" bordered>
                <a-descriptions-item label="决策树类型">
                  {{treeParams.type}}
                </a-descriptions-item>
                <a-descriptions-item label="划分标准">
                  {{treeParams.splitter}}
                </a-descriptions-item>
                <a-descriptions-item label="决策树深度/最大深度">
                  {{treeResult.depth}}/{{treeParams.max_depth}}
                </a-descriptions-item>
                <a-descriptions-item label="数据集划分比例">
                  {{treeParams.proportion}}
                </a-descriptions-item>
                <a-descriptions-item label="决策树准确率">
                  {{ (Number(treeResult.score.toFixed(4)) * 100).toFixed(2) }}%
                </a-descriptions-item>
                <a-descriptions-item label="决策树构建时间(ms)">
                  {{treeResult.time.toFixed(2)}}
                </a-descriptions-item>
              </a-descriptions>
            </div>
            <br>
            <div class="imageContainer">
              <a-spin size="large" :spinning="imageMsg"/>
              <a-image :src="treeResult.imageUrl"/>
            </div>
            <br>
          </div>
          <div>
            <a-button @click="tableClear" style="margin-right: 20px;float: right" danger>清空</a-button>
            <a-table :columns="columns_2" :data-source="DecisionTreeStore.MLTreeTableAnswer" rowKey="key" :pagination="false" bordered>
              <template #bodyCell="{ column, index }">
                <template v-if="column.dataIndex==='num'">
                  {{index + 1}}
                </template>
                <template v-else-if="column.dataIndex==='operation'&&index<DecisionTreeStore.row_num">
                    <a @click="deleteRow(index)">删除</a>
                </template>
                <template v-else-if="index<DecisionTreeStore.row_num">
                  <template v-if="column.dataIndex==='image'">
                    <a-image :src="DecisionTreeStore.MLTreeTableAnswer[index][column.dataIndex]"/>
                  </template>
                  <template v-else>
                    {{DecisionTreeStore.MLTreeTableAnswer[index][column.dataIndex]}}
                  </template>
                </template>
              </template>
            </a-table>
            <a-textarea v-model:value=DecisionTreeStore.MLTreeSummary style="margin-top: 5px;" placeholder="请写下总结的实验规律"  showCount :maxlength="1000" :rows="4"/>
          </div>
        </a-collapse-panel>
      </a-collapse>
    </a-collapse-panel>
  </a-collapse>
</template>

<script setup lang="ts">
  import { generateMLtree, ML_tree_params_type, ML_tree_result_type } from '../api/generateTree';
  import { useDecisionTreeStore } from '../stores';
  import { ref } from 'vue';
  import { message } from 'ant-design-vue';

  let DecisionTreeStore = useDecisionTreeStore()

  const columns = [
    {
      title: "决策树种类",
      dataIndex: "type",
      customHeaderCell: () => ({ style: "font-size: 15px;"}),
    },
    {
      title: "特征选择标准",
      dataIndex: "method",
      customHeaderCell: () => ({ style: "font-size: 15px;"}),
    },
    {
      title: "连续值处理",
      dataIndex: "continuous",
      customHeaderCell: () => ({ style: "font-size: 15px;"}),
    },
    {
      title: "缺失值处理",
      dataIndex:"missing",
      customHeaderCell: () => ({ style: "font-size: 15px;"}),
    }
  ]
  const methodOptions=[
    {
      label: '信息增益',
      value: '信息增益'
    },
    {
      label: '信息增益率',
      value: '信息增益率'
    },
    {
      label: '基尼增益',
      value: '基尼增益'
    },
  ]
  const supportOptions= [
    {
      label: '支持',
      value: '支持'
    },
    {
      label: '不支持',
      value: '不支持' }
  ]

  const columns_2 = [
    {
      title: "序号",
      dataIndex: "num",
      width: '5%',
      customHeaderCell: () => ({ style: "font-size: 15px;"}),
    },
    {
      title: "决策树种类",
      dataIndex: "type",
      width: '10%',
      customHeaderCell: () => ({ style: "font-size: 15px;"}),
    },
    {
      title: "特征划分标准",
      dataIndex: "splitter",
      width: '10%',
      customHeaderCell: () => ({ style: "font-size: 15px;"}),
    },
    {
      title: "最大深度",
      dataIndex: "max_depth",
      width: '7%',
      customHeaderCell: () => ({ style: "font-size: 15px;"}),
    },
    {
      title: "数据集划分比例（验证集/总数）",
      dataIndex: "proportion",
      width: '18%',
      customHeaderCell: () => ({ style: "font-size: 15px;"}),
    },
    {
      title: "决策树深度",
      dataIndex: "depth",
      width: '8%',
      customHeaderCell: () => ({ style: "font-size: 15px;"}),
    },
    {
      title: "准确率",
      dataIndex: "accuracy",
      customHeaderCell: () => ({ style: "font-size: 15px;"}),
    },
    {
      title: "耗时(ms)",
      dataIndex: "time",
      customHeaderCell: () => ({ style: "font-size: 15px;"}),
    },
    {
      title: "图片",
      dataIndex: "image",
      width: '15%',
      customHeaderCell: () => ({ style: "font-size: 15px;"}),
    },
    {
      title: "操作",
      dataIndex: "operation",
      width: '7%',
      customHeaderCell: () => ({ style: "font-size: 15px;"}),
    },
  ]
  function tableClear(){
    DecisionTreeStore.row_num=0
    DecisionTreeStore.MLTreeTableAnswer=[
      {
        type:"",
        splitter:"",
        max_depth:"",
        proportion:"",
        depth:"",
        accuracy:"",
        time:"",
        image:"",
      },
      {
        type:"",
        splitter:"",
        max_depth:"",
        proportion:"",
        depth:"",
        accuracy:"",
        time:"",
        image:"",
      },
      {
        type:"",
        splitter:"",
        max_depth:"",
        proportion:"",
        depth:"",
        accuracy:"",
        time:"",
        image:"",
      },
      {
        type:"",
        splitter:"",
        max_depth:"",
        proportion:"",
        depth:"",
        accuracy:"",
        time:"",
        image:"",
      },
      {
        type:"",
        splitter:"",
        max_depth:"",
        proportion:"",
        depth:"",
        accuracy:"",
        time:"",
        image:"",
      },
      {
        type:"",
        splitter:"",
        max_depth:"",
        proportion:"",
        depth:"",
        accuracy:"",
        time:"",
        image:"",
      },
      {
        type:"",
        splitter:"",
        max_depth:"",
        proportion:"",
        depth:"",
        accuracy:"",
        time:"",
        image:"",
      },
      {
        type:"",
        splitter:"",
        max_depth:"",
        proportion:"",
        depth:"",
        accuracy:"",
        time:"",
        image:"",
      },
      {
        type:"",
        splitter:"",
        max_depth:"",
        proportion:"",
        depth:"",
        accuracy:"",
        time:"",
        image:"",
      },
      {
        type:"",
        splitter:"",
        max_depth:"",
        proportion:"",
        depth:"",
        accuracy:"",
        time:"",
        image:"",
      },
    ]
  }
  function deleteRow(index:any){
    DecisionTreeStore.row_num--
    DecisionTreeStore.MLTreeTableAnswer.splice(index, 1)
    DecisionTreeStore.MLTreeTableAnswer.push({
      type:"",
      splitter:"",
      max_depth:"",
      proportion:"",
      depth:"",
      accuracy:"",
      time:"",
      image:"",
    },)
  }

  let tree_type = ref("C4.5")
  let tree_splitter=ref("best")
  let tree_max_depth=ref(5)
  let tree_proportion=ref(0.2)

  let treeParams = ref<ML_tree_params_type>({
    type:"C4.5",
    splitter: "best",
    max_depth: 5,
    proportion: 0.2
  })
  let imageMsg = ref(false)
  let treeResult = ref<ML_tree_result_type>({
    imageUrl: null,
    score: null,
    time: null,
    depth: null
  })

  function handleBlur(flag:any){
    switch(flag){
      case(0):
        if (tree_max_depth.value === null) {
          tree_max_depth.value = 5;
        }
        break
      case(1):
        if (tree_proportion.value === null) {
          tree_proportion.value = 0.2;
        }
    }

  }
  async function generateTree(){
    if(DecisionTreeStore.row_num===10){
      message.warn("当前表格已满请删除后再生成")
      return
    }
    imageMsg.value=true
    treeResult.value.imageUrl=null
    treeParams.value.type = tree_type.value
    treeParams.value.splitter = tree_splitter.value
    treeParams.value.max_depth = tree_max_depth.value
    treeParams.value.proportion = tree_proportion.value
    generateMLtree(treeParams.value).then((result)=> {
      imageMsg.value = false
      treeResult.value.imageUrl = result.imageUrl
      treeResult.value.score = result.score
      treeResult.value.time = result.time
      treeResult.value.depth = result.depth

      DecisionTreeStore.MLTreeTableAnswer[DecisionTreeStore.row_num++]={
        type: treeParams.value.type,
        splitter: treeParams.value.splitter,
        max_depth: treeParams.value.max_depth.toString(),
        proportion: treeParams.value.proportion.toString(),
        depth: treeResult.value.depth.toString(),
        accuracy: (treeResult.value.score * 100).toFixed(2) + "%",
        time: treeResult.value.time.toFixed(2),
        image: treeResult.value.imageUrl,
      }
    })
      .catch(()=>{
        treeResult.value.imageUrl=""
      })
  }
</script>

<style scoped>
  .content{
    text-indent: 2em;
    margin-left: 40px;
    margin-right: 20px;
  }
  .imageContainer{
    text-align: center;
  }

  h3,h4{
    font-weight: bold;
  }

  a{
    margin-right: 20px;
  }
</style>