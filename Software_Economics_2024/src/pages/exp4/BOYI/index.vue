<template>

  <a-space direction="horizontal">
    <a-button @click="showExperimentTwo = !showExperimentTwo">
      {{ showExperimentTwo ? '显示实验二' : '显示实验一' }}
    </a-button>
    <div v-if="showExperimentTwo==true">角色：{{mystore.role}}</div>
    <div v-if="showExperimentTwo==false">角色：{{mystore.Role}}</div>
  </a-space>


<div v-if="showExperimentTwo">
  <div>
    <a-steps v-model:current="current" size="small">
      <a-step v-for="item in steps" :key="item.title" :title="item.title"/>
    </a-steps>
    <div class="steps-content">
      <div v-if="current == 0" ref="content1" class="exp-step-card">
        <h2>实验原理 </h2>

        <h3>无不确定性的委托人-代理人</h3>
        <p class="content">
          在经济社会中，有大量一方委托另一方完成特定工作的情况。比如，企业雇佣工人进行生产，店主雇佣店员销售商品，企业聘请经理管理企业等，本实验使用努力成果不确定但可监督的委托人-代理人模型来展开完成实验。
        </p>
        <h3>委托人-代理人模型简介</h3>
        <p class="content">先假设代理人的工作成果美欧不确定性，代理人的产出是努力程度的确定函数，委托人可以根据成果掌握代理人的工作情况。此外，假设委托关系是基于一种标准合同，委托人的选择是提供或者不提供这份合同
          ，而不是支付给代理人的报酬或者报酬函数。代理人的选择有两层，首先是是否接受这份合同，其次是努力工作还是偷懒。这是一个两个博弈方之间的三段动态博弈，如下如所示
        </p>
        <div class="image-center"><img src="./image/queding.png" width="500"></div>
        <p class="content">1.博弈方1是委托人，博弈方2是代理人。第一阶段委托人选择是否委托。委托人选择不委托。自己的利益R(0)可能是0甚至是负值，选择不委托代理人的利益为0.
          委托人选择委托，则轮到代理人选择。
        </p>
        <p class="content">2.代理人先在第二阶段选择是否接受委托。若选择不接受委托，结果与委托人不委托没有区别，双方得益与第一阶段委托人不委托相同。如果选择接受委托，
          还需要在第三阶段选择是否努力。
        </p>
        <p class="content">
          3.代理人在第三阶段选择努力(高努力水平)，还是偷懒(低努力水平)。如果代理人选择努力，那么委托人得到较高产的R(E)，但是要支付较高的报酬w(E)给代理人，代理人得到较高报酬w(E)，但有较高负效用-E，
          委托人和代理人得益分别是R(E)-w(E)和w(E)-E。如果代理人选择偷懒，委托人得到较低产出R(S)，给代理人支付较低报酬w(S)，代理人得到较低报酬w(S)，但只有较低负效用-S，此时双方得益为R(S)-w(S)和w(S)-S。
        </p>
        <p class="content">4.因为这个博弈中，两博弈方都清楚自己和对方的得益情况，也都能观察到对方的选择(即使委托人无法观察代理人第三阶段的选择，因为委托人仍然完全清楚代理人的选择)，因此本例子的博弈是一个
          完全且完美信息的动态博弈，适合用逆推归纳法进行分析。为了方便实验的推进，下面通过举例对本模型进行分析
        </p>
        <p class="content">首先分析代理人第三阶段是否努力的选择。根据理性博弈方的决策原则不难知道，如果w(E) - E > w(S) -
          S，也就是w(E) >w(S) + E -S 时，代理人会选择努力。
          上述不等式也称为代理人的"激励相容约束"，也就是委托人在提出委托和代理人接受委托的前提下，促使代理人努力工作必须满足的条件。第二个不等式的经济意义是，只有当努力工作的
          代理人得到的报酬达到，在偷懒代理人得到报酬以上有一个不低于能补偿努力比偷懒更大负效用的增加额时，代理人才可能自觉选择努力工作。反过来，如果w(E)
          - S > w(E) - E,代理人
          肯定会选择偷懒，则该不等式是代理人偷懒的"激励相容约束"。
        </p>
        <p class="content">用一个数值例子进一步说明。假设努力的投入产出函数为R(e)=10e-e^2,代理人努力即努力水平2单位，偷懒即努力水平为1单位，而且努力和偷懒的负效用等于努力水平数值，也就是E
          = 2，S = 1。因此，R(0)=0，R(E)=16,R(S)=9。再假设w(E)=4,w(S)=2。
          这样，该博弈的得益结构如下图所示。
        </p>
        <div class="image-center"><img src="./image/lizi.png" width="400"></div>
        <p class="content">根据前面的分析和结论不难看出，在这个数值例子中，因为w(E) - E = 2 > w(S) - S =
          1满足促使代理人努力的激励相容原理，w(E) - E = 2 > 0满足代理人接受委托的参与约束，R(E) - w(E) = 12 > R(0) = 0
          满足委托人提出委托的条件，因此这个数值使得委托人选择委托
          ，代理人接受委托并努力工作，因此该博弈是可以预测结果的。
        </p>
        <h3>本实验为了增加实验的趣味性，选择对上述的模型拓展</h3>
        <h3>有不确定性但可监督的委托人-代理人博弈</h3>
        <p class="content">由于代理人的努力和成果之间不完全一致，因此有根据工作情况还是成果支付报酬的选择问题。在委托人可以完全监督代理人工作的情况下，通常是根据代理人的工作情况
          而不是工作成果支付报酬。这意味He产出不确定性的风险完全由委托人承担，理由是风险主要来源与环境或随机因素，与代理人的行为无关
        </p>
        <p class="content">
          简单起见，本实验假设模型中不确定性表现为：由20和10单位两种可能的产出，代理人努力时产出20的概率为0.9，产出10的概率为0.1；代理人偷懒时产出20的概率为0.1，产出10的概率为0.9.再假设R(0)
          = 0，其他与前面的模型一样。
        </p>
        <p class="content">模型图参考如下图所示
        </p>
        <div class="image-center"><img src="./image/dongtaiboyi.png" width="400"></div>
        <p class="content">假设代理人选择的是接受委托并努力工作，那么委托人0.9的概率获得对应高产的得益，0.1的概率得到对应低产出的得益。因此对于风险中性的委托人来说，如果委托的期望得益大于不委托，即
          0.9 * (20 - w(E)) + 0.1 * (10 - w(E)) > 0
          应该选择委托；如果委托的期望得益小于不委托，即 0.9 * (20 - w(E)) + 0.1 * (10 - w(E)) &lt; 0 应该选择不委托
        </p>
        <p class="content">在代理人选择的是接受委托并偷懒的情况下，则委托人0.1的概率获得对应高产出的得益，0.9的概率得到对应低产出的得益。如果委托的期望得益大于不委托，即
          0.1 * (20 - w(S)) + 0.9 * (10 - w(S)) > 0 应该选择委托；如果委托的期望得益小于不委托，即 0.1 * (20 - w(S)) + 0.9
          * (10 -w(S)) &lt; 0
          则应该选择不委托。
        </p>
        <p class="content">本实验，给出上述模型中的E,S,w(E),w(S)的数值，则可以得出具体的结论，同时，本实验当中，你会被自动分配一个角色
        </p>
      </div>
      <div v-if="current == 1" ref="content2" class="exp-step-card">
        <h3>高产的投入产出函数:R(e)=2000</h3>
        <h3>低产的投入产出函数:R(e)=1000</h3>
        <h3>代理人努力时的努力水平（负效用）：E=200</h3>
        <h3>代理人偷懒时的努力水平（负效用）：E=100</h3>
      </div>
      <div v-if="current == 2" ref="content3" class="exp-step-card">
        <div v-if="mystore.matchUser == ''">
          博弈开始，请点击右侧分配角色来分配你的角色和对手。
          <span v-if="mystore.role !== ''">你当前的角色是：{{ mystore.role }}</span>
          &nbsp;&nbsp;
          <a-button :disabled="mystore.role !== ''" @click="onGetRole" class="role-button">分配角色</a-button>
          &nbsp;
          <a-button :disabled="mystore.role === '' || mystore.matchUser !== ''" @click="onGetRival" class="match-button">匹配对手</a-button>
        </div>
        <div v-else>
          本实验当中您是 {{ mystore.role }} 角色，您的对手是：{{ mystore.matchUser }}，请选择下一步开始实验
        </div>
      </div>
      <div v-if="current == 3" ref="content4" class="exp-step-card">
        <div v-if="mystore.role === '委托人'">
          <div v-if="mystore.acceptPrincipal === undefined">
            <div>您需要设置代理人努力或偷懒时所获得的报酬</div>
            <a-space direction="vertical">
              <div>请输入努力时获得的报酬(500-1000)</div>
              <a-space direction="horizontal">
                <a-input-number class="hard-earn" v-model:value="HardEarn" :disabled="disable10" placeholder="努力报酬" />
                <a-button class="hard" @click="Hard">确定</a-button>
              </a-space>
              <div>请输入偷懒时获得的报酬(100-500)</div>
              <a-space direction="horizontal">
                <a-input-number class="lazy-earn" v-model:value="NotHard" :disabled="disable11" placeholder="偷懒报酬" />
                <a-button class="lazy" @click="Nothard">确定</a-button>
              </a-space>
            </a-space>
          </div>
          <div v-if="mystore.acceptPrincipal == true">
            代理人同意为您代理，请选择下一步
          </div>
          <div v-if="mystore.acceptPrincipal == false">
            <div>委托人拒绝您帮他代理，实验结束！！！</div>
          </div>
        </div>

        <div v-if="mystore.role === '代理人'">
          <div v-if="principal">
            <div>委托人已经同意您帮他委托，努力时获得的报酬时{{HardEarn}},偷懒时获得的报酬是{{NotHard}}</div>
            <a-radio-group v-model:value="mystore.acceptPrincipal" @change="acceptChange">
              <a-radio :value="true">接受</a-radio>
              <a-radio :value="false">拒绝</a-radio>
            </a-radio-group>
          </div>
          <div v-else>
            <div>请等待委托人做出决策是否委托您</div>
          </div>
        </div>
      </div>
      <div v-if="current == 4" ref="content5" class="exp-step-card">
        <div v-if="mystore.role === '委托人'">
          <div v-if="mystore.strive === undefined">
            代理人正在做出决策，请等待......
          </div>
          <div v-else>
            代理人已经做出决策，请点击下一步推进实验
          </div>
        </div>
        <div v-if="mystore.role === '代理人'">
          <div>您已确认为委托人代理，请选择偷懒还是努力来推进实验，请选择最符合您利益的方式</div>
          <a-radio-group v-model:value="mystore.strive" @change="striveChange">
            <a-radio :value="true">努力</a-radio>
            <a-radio :value="false">偷懒</a-radio>
          </a-radio-group>
        </div>
      </div>
      <div v-if="current == 5" ref="content6" class="exp-step-card">
        <div>由于外界环境以及一些不确定的因素，会影响工作的高产或者低产以及工作结果</div>
        <div>您因为不确定因素造成的获得因素是：<h3>{{ mystore.bug }}</h3></div>
      </div>
      <div v-if="current == 6" ref="content7" class="exp-step-card">
        <div>实验结束</div>
        <div>委托人获得的报酬是：{{ mystore.principalPrice }}</div>
        <div>代理人获得的报酬是：{{ mystore.agentPrice }}</div>
      </div>

      <div v-if="current == 7" ref="content9" class="exp-step-card">
        <h2>
          实验心得
        </h2>
        <a-textarea v-model:value="mystore.summary"/>
      </div>
    </div>
    <div class="steps-action">
      <a-button v-if="current < steps.length - 1" style="float: right;" type="primary"
                @click="next">
        <step-forward-outlined/>
        下一步
      </a-button>
      <a-button v-if="current > 0" style="margin-left: 8px" @click="prev">
        <step-backward-outlined/>
        上一步
      </a-button>
    </div>
    <div v-if="current > 0" style="margin-top: 20px; display: flex;justify-content: flex-end;">
      <a-button @click="clearData">清空数据</a-button>
    </div>

  </div>

</div>
<div v-else>
  <a-steps v-model:current="current0" size="small">
    <a-step v-for="item in steps1" :key="item.title" :title="item.title" />
  </a-steps>
  <div class="steps-content">
    <div class="exp-step-card" v-if="current0 == 0" ref="content11">






      <h1>实验原理 </h1>
      <div>二手车交易正是不完美信息动态博弈问题的典型代表，如果能弄清楚二手车中的博弈关系及其各种均衡
        可以对此类博弈问题有更加深刻的理解，本实验采用双价格二手车的模型来进行实验，下面为了方便实验的操作性，一一介绍单一价格二手车和双价格二手车交易模型</div>
      <h2>单一价格二手车交易模型</h2>
      <div class="image-center"><img src="./image/Dershou.png" width="500"></div>
      <div>  在市场上购买车辆，有车况好和车况坏的情况，此模型假设二手车有好、差两种情况，对买方价值分别为V和W，
        V>W，再假设买方想买好车，不想买差车，因此卖方若想卖出车子，不管车况好、差都只能当好车卖，因此只有一种价格P。
        这也意味着，车况差时卖方必须花成本伪装欺骗买方，伪装成本为C，所以，二手车交易可以用上图的交易模型表示
      </div>
      <div> 如果P>C,V>P>W,即车价大于伪装费用，车况好对买方价值大于价格，车况差价值小于价格。那么车况好的时候成交对双方都有利，车况差
        时卖方得利、买方损失，车况好时未成交双方虽然没有损失，但是丧失了得益机会，车况差时卖方想卖出去会白白损失伪装成本，买方可以庆幸自己没有上当受骗</div>
      <div>在价值、价格和伪装成本满足上述条件时，买卖双方的积极选择对自己都有一些风险性，保守选择则可能丧失潜在的交易利益
        。因此，当买方无法确定车况的情况下，买方的任何选择都不是绝对的上策，卖方也有因为花钱伪装卖不出去受损的危险，双方的决策和博弈结果有许多可能性。</div>
      <H2>双价格二手车交易模型</H2>
      <div class="image-center"><img src="./image/Sershou.png" width="500"></div>
      <div>仍然假设车况有好、差两种情况，但卖方的选择不是卖或不卖，而是卖高价Ph还是低价Pt。设卖方不仅车况差时可以选择卖高价或者低价，
        车况好时也可以选择高、低两种价格。再假设只有车况差而卖方想卖高价时，才需要伪装费用有C，其他方面与单一价格模型相同。双价格二手车
        交易模型如上图所示。其中得益数组第一个数字为卖方得益，即其纯收益；第二个数字为买方得益，即消费者剩余，价值与价格之差。根据模型的基本意义，
        V>W且Ph>Pl。</div>
      <div>本实验中的高价和低价都需要由卖方自己估价给出，价值由买方自己估价给出，并且买方通过卖方给出的标价选择是否要购买，同时，C(车况差时的维修费用)不等于0，车况的好和差由系统随机给出</div>
    </div>
    <div class="exp-step-card" v-if="current0 == 1" ref="content12">
      <div v-if="mystore.matchUser1 == ''">
        博弈开始，请点击右侧分配角色来分配你的角色和对手。
        <span v-if="mystore.Role!== ''">你当前的角色是：{{ mystore.Role }}</span>
        &nbsp;&nbsp;
        <a-button :disabled="mystore.Role !== ''" @click="onGetRole1">分配角色</a-button>
        &nbsp;
        <a-button :disabled="mystore.Role === '' || mystore.matchUser1 !== ''" @click="onGetRival1">匹配对手</a-button>
        <a-button @click="CallAI">人机对弈</a-button>
      </div>
      <div v-else>
        本实验当中您是 {{ mystore.Role }} 角色，您的对手是：{{ mystore.matchUser1 }}，请选择下一步开始实验
      </div>

    </div>
    <div class="exp-step-card" v-if="current0 == 2" ref="content13">

      <div v-if="mystore.Role=='卖方'">
        <div v-if="situation=='车况差'">
          很不幸，当前由于市场车况较差，您需要将现在市场价格为75000元估价的雷克萨斯卖给买家，当然，您可以选择先将车修理好以高价卖出，也可以选择以低价直接卖出，同时，在两年以内的汽车打九折，
          两年以上五年以内的汽车打七五折，五年以上十年以内的需要五折出售，十年以上二五折出售，您需要选择的高价区间在75000-8000左右，如果不修理直接卖出，您需要选择的低价区间在70000-75000元之间，一旦选择，不得修改
        </div>
      </div>
      <div v-if="mystore.Role=='买方'">
        <div v-if="situation=='车况差'">
          很不幸，当前市场车况较差，这辆雷克萨斯现在的市场价格为75000元，您可以在70000-80000区间内制定这辆在您心中的价值，一旦确定，不得修改
        </div>
      </div>


      <div v-if="mystore.Role=='买方'">
        <div v-if="situation=='车况好'">
          现在有一辆市场二手车价格为90000的雷克萨斯，由于现在这辆车市场很好，您需要给出价格在80000-100000范围内你能接受的价格，一旦确定，不得修改
        </div>
      </div>
      <div v-if="mystore.Role=='卖方'">
        <div v-if="situation=='车况好'">
          您现在需要将市场上价格为90000估价雷克萨斯卖给购买方，但是您想要获利比较多一点，所以决定为这辆车制定高价和低价，您可以在80000-90000区间制
          定低价，90000-100000区间制定高价来获得更高的利润，一旦确定价格，不得修改
        </div>
      </div>


      <div v-if="mystore.Role=='卖方'">
        <div v-if="situation==undefined">
          <div>由于市场的多变化性因素，会导致车况有好和差两种情况,请点击按钮获取当前市场的车况</div>
          <a-button @click="ChooseSituation">车况好</a-button>
          <a-button @click="ChooseSituation1">车况差</a-button>
        </div>
      </div>
      <div v-if="mystore.Role=='买方'">
        <div v-if="situation==undefined">
          <div>正在获取当前车辆的状况......</div>
        </div>
      </div>

    </div>
    <div class="exp-step-card" v-if="current0 == 3" ref="content14">
      <div v-if="situation=='车况差'">

        <div v-if="mystore.Role=='买方'">
          <div>请输入70000-80000之间的价格</div>
          <a-space direction="horizontal">
            <a-input-number v-model:value="mystore.BadSituationValue" :disabled="disable1" placeholder="输入价格" />
            <a-button @click="InputLock">确定</a-button>
          </a-space>
        </div>

        <div v-if="mystore.Role=='卖方'">
          <a-space direction="vertical">
            <div>请输入高价(75000-80000)</div>
            <a-space direction="horizontal">
              <a-input-number v-model:value="mystore.BadSituationHighPrice" :disabled="disable2" placeholder="输入价格" />
              <a-button @click="InputLock1">确定</a-button>
            </a-space>
            <div>请输入低价(70000-75000)</div>
            <a-space direction="horizontal">
              <a-input-number v-model:value="mystore.BadSituationLowPrice" :disabled="disable3" placeholder="输入价格" />
              <a-button @click="InputLock2">确定</a-button>
            </a-space>
            <div>请选择使用年限</div>
            <a-space direction="horizontal">
              <a-input-group >
                <a-select v-model:value="value17" :disabled="disable7" style="width: 100px">
                  <a-select-option value="1">2年内</a-select-option>
                  <a-select-option value="2">2-5年</a-select-option>
                  <a-select-option value="3">5-8年</a-select-option>
                  <a-select-option value="4">8年以上</a-select-option>
                </a-select>
              </a-input-group>
              <a-button @click="FixedpriceO">确定</a-button>
            </a-space>
          </a-space>
        </div>
      </div>
      <div v-if="situation=='车况好'">

        <div v-if="mystore.Role=='买方'">
          <div>请输入70000-90000之间的价格</div>
          <a-space direction="horizontal">
            <a-input-number v-model:value="mystore.GoodSituationValue" :disabled="disable4" placeholder="输入价格" />
            <a-button @click="GoodInputLock">确定</a-button>
          </a-space>
        </div>

        <div v-if="mystore.Role=='卖方'">
          <a-space direction="vertical">
            <div>请输入高价(80000-90000)</div>
            <a-space direction="horizontal">
              <a-input-number v-model:value="mystore.GoodSituationHighPrice" :disabled="disable5" placeholder="输入价格" />
              <a-button @click="GoodInputLock1">确定</a-button>
            </a-space>
            <div>请输入低价(70000-80000)</div>
            <a-space direction="horizontal">
              <a-input-number v-model:value="mystore.GoodSituationLowPrice" :disabled="disable6" placeholder="输入价格" />
              <a-button @click="GoodInputLock2">确定</a-button>
            </a-space>

          </a-space>
        </div>
      </div>

    </div>
    <div class="exp-step-card" v-if="current0 == 4" ref="content15">
      <div v-if="situation=='车况好'">

        <div v-if="mystore.Role=='买方'">卖方给出的价格是{{price}},您是否选择购买
          <a-radio-group v-model:value="buy" @change="Buyerchange">
            <a-radio :value="'true'">购买</a-radio>
            <a-radio :value="'false'">不购买</a-radio>
            <a-radio :value="'2'">议价</a-radio>
          </a-radio-group>
        </div>

        <div v-if="mystore.Role=='卖方'">
          <div v-if="buy==undefined">
            等待买方做出决策。。。。。。
          </div>
          <div v-if="buy=='false'">
            买方不选择购买这辆车
          </div>
          <div v-if="buy=='true'">
            买方选择购买这辆车！请点击下一步查看您的收益
          </div>
          <div v-if="buy=='2'">
            您所给出的价格超出了买方的预期，买方提出议价
          </div>
        </div>
      </div>
      <div v-if="situation=='车况差'">

        <div v-if="mystore.Role=='买方'">卖方给出的价格是{{price}},您是否选择购买
          <a-radio-group v-model:value="buy" @change="Buyerchange">
            <a-radio :value="'true'">购买</a-radio>
            <a-radio :value="'false'">不购买</a-radio>
            <a-radio :value="'2'">议价</a-radio>
          </a-radio-group>

        </div>

        <div v-if="mystore.Role=='卖方'">
          <div v-if="buy==undefined">
            等待买方做出决策。。。。。。
          </div>
          <div v-if="buy=='false'">
            买方不选择购买这辆车
          </div>
          <div v-if="buy=='true'">
            买方选择购买这辆车！请点击下一步查看您的收益
          </div>
          <div v-if="buy=='2'">
            买方提出议价，即将跳转议价界面
          </div>
        </div>
      </div>

    </div>
    <div class="exp-step-card" v-if="current0 == 5" ref="content16">

      <div v-if="mystore.Role=='买方'">
        <div v-if="sale==undefined&&Higerprice==undefined">
          <div v-if="CustomerValue-price<=0">
            等待卖方给出合理的价格。。。
          </div>

        </div>
        <div v-if="sale==undefined&&Higerprice!=undefined">
          <div v-if="Higerprice-Lowerprice<=0">
            等待卖方给出合理的价格。。。
          </div>

        </div>
        <div v-if="sale==0">
          卖方不选择降价，成交失败
        </div>
        <div v-if="sale==2&&CustomerValue-Lowerprice<0">
          等待卖方给出合理的价格。。。
        </div>
        <div v-if="sale==1">
          卖方给出的价格为{{Lowerprice}}
          <div v-if="Lowerprice-CustomerValue>CustomerValue*0.005&&Higerprice==undefined">
            您是否提高价格
            <a-space direction="horizontal">
              <a-button @click="HigerPrice">提价</a-button>
              <a-button @click="Notbuy">不买</a-button>
            </a-space>

          </div>

          <div v-if="Lowerprice-Higerprice>Higerprice*0.005&&Higerprice!=undefined">
            您是否提高价格
            <a-space direction="horizontal">
              <a-button @click="HigerPrice">提价</a-button>
              <a-button @click="Notbuy">不买</a-button>
            </a-space>

          </div>
          <div v-if="Lowerprice-Higerprice<Higerprice*0.005&&Higerprice!=undefined">
            卖方给出的价格符合您的预期！成交
          </div>
          <div v-if="Lowerprice-CustomerValue<CustomerValue*0.005&&Higerprice==undefined">
            卖方给出的价格符合您的预期！成交
          </div>
        </div>
        <div v-if="sale==3">
          <a-space direction="vertical">
            <div>请输入提价价格</div>
            <a-space direction="horizontal">
              <a-input-number v-model:value="Higerprice" :disabled="disable9" placeholder="输入价格" />
              <a-button @click="HigerConfirm">确定</a-button>
            </a-space>
          </a-space>
        </div>
      </div>



      <div v-if="mystore.Role=='卖方'">
        <div v-if="sale==undefined&&Higerprice==undefined">
          <a-space direction="vertical">
            <div v-if="CustomerValue-price<=0">
              买方给出的价格是{{CustomerValue}},您给出的价格是{{price}},您是否选择降价
            </div>
            <a-space direction="horizontal">
              <a-button @click="LowerPrice">降价</a-button>
              <a-button @click="Notsale">不卖</a-button>
            </a-space>
          </a-space>
        </div>
        <div v-if="sale==undefined&&Higerprice!=undefined">
          <a-space direction="vertical">
            <div v-if="Higerprice-Lowerprice<=0">
              买方给出的价格是{{Higerprice}},您给出的价格是{{Lowerprice}}，您是否选择降价
            </div>
            <a-space direction="horizontal">
              <a-button @click="LowerPrice">降价</a-button>
              <a-button @click="Notsale">不卖</a-button>
            </a-space>
          </a-space>
        </div>
        <div v-if="sale==0">您不考虑降价，成交失败</div>
        <div v-if="sale==1">
          <a-space direction="vertical">
            <div>请输入降价价格</div>
            <a-space direction="horizontal">
              <a-input-number v-model:value="Lowerprice" :disabled="disable8" placeholder="输入价格" />
              <a-button @click="LowerConfirm">确定</a-button>
            </a-space>
          </a-space>
        </div>
        <div v-if="sale==2">
          <a-space direction="vertical">
            <div>再次进入议价环节，买方对于您的价格还是不满意，是否降价</div>
            <a-space direction="horizontal">
              <a-button @click="LowerPrice">降价</a-button>
              <a-button @click="Notsale">不卖</a-button>
            </a-space>
          </a-space>
        </div>
      </div>



    </div>


    <div class="exp-step-card" v-if="current0 == 6" ref="content17">
      <h1>实验结束</h1>
      <div v-if="mystore.Role=='卖方'">
        <div v-if="buy=='false'">
          交易失败，您的收益为0
        </div>
        <div v-if="buy=='true'">
          <div>您的成交价格为：{{price}}</div>
          <div v-if="situation=='车况差'">
          <div v-if="value17=='1'">您的收益为：{{price-((mystore.BadSituationHighPrice+mystore.BadSituationLowPrice)/2)*0.9}}</div>
          <div v-if="value17=='2'">您的收益为：{{price-((mystore.BadSituationHighPrice+mystore.BadSituationLowPrice)/2)*0.75}}</div>
          <div v-if="value17=='3'">您的收益为：{{price-((mystore.BadSituationHighPrice+mystore.BadSituationLowPrice)/2)*0.5}}</div>
          <div v-if="value17=='4'">您的收益为：{{price-((mystore.BadSituationHighPrice+mystore.BadSituationLowPrice)/2)*0.25}}</div>
          </div>
          <div v-if="situation=='车况好'">
            <div>您的收益为：{{price-((mystore.GoodSituationHighPrice+mystore.GoodSituationLowPrice)/2)}}</div>

          </div>
        </div>
        <div v-if="buy=='2'">
          <div v-if="sale==0">
            交易失败，您的收益为0
          </div>
          <div v-else>
          <div>您的成交价格为：{{Lowerprice}}</div>
          <div v-if="situation=='车况差'">
          <div v-if="value17=='1'">您的收益为：{{Lowerprice-((mystore.BadSituationLowPrice+mystore.BadSituationHighPrice)/2)*0.9}}</div>
          <div v-if="value17=='2'">您的收益为：{{Lowerprice-((mystore.BadSituationLowPrice+mystore.BadSituationHighPrice)/2)*0.75}}</div>
          <div v-if="value17=='3'">您的收益为：{{Lowerprice-((mystore.BadSituationLowPrice+mystore.BadSituationHighPrice)/2)*0.5}}</div>
          <div v-if="value17=='4'">您的收益为：{{Lowerprice-((mystore.BadSituationLowPrice+mystore.BadSituationHighPrice)/2)*0.25}}</div>
          </div>
          <div v-if="situation=='车况好'">
            <div>您的收益为：{{Lowerprice-((mystore.GoodSituationLowPrice+mystore.GoodSituationHighPrice)/2)}}</div>

          </div>
        </div>
        </div>
      </div>
      <div v-if="mystore.Role=='买方'">
        <div v-if="buy=='true'">
          您已购买这辆车，价格为{{price}}
        </div>
        <div v-if="buy =='false'">
          您未成交这笔交易
        </div>
        <div v-if="buy =='2'">
          <div v-if="sale==0">
            您未成交这笔交易
          </div>
          <div v-else>
            您已购买这辆车，价格为{{Lowerprice}}
          </div>
        </div>
      </div>
    </div>
    <div v-if="current0 == 7" ref="content17" class="exp-step-card">
      <h2>
        实验心得
      </h2>
      <a-textarea v-model:value="mystore.summary1"/>
    </div>
  </div>

  <div class="steps-action">
    <a-button v-if="current0 < steps1.length - 1" type="primary" style="float: right;"
              @click="next1"><step-forward-outlined />下一步</a-button>
    <a-button v-if="current0 == steps1.length - 1" type="primary" style="float: right;" @click="submit">
      提交
    </a-button>
    <a-button v-if="current0 > 0" style="margin-left: 8px" @click="prev1"><step-backward-outlined />上一步</a-button>
  </div>
  <div v-if="current0 > 0" style="margin-top: 20px; display: flex;justify-content: flex-end;">
    <a-button  @click="clearData1" >清空数据</a-button>
    <a-button @click="saveData">保存实验数据</a-button>
  </div>
</div>
<a-modal
      v-model:visible="isModalOpen"
      title="Title"
      @ok = "handleOk"
  >
      <p>{{ modalText }}</p>
    </a-modal>
  <a-modal
    v-model:visible="isModalOpen1"
    title="Title"
    @ok = "handleOk1"
  >
    <p>{{ modalText1() }}</p>
  </a-modal>
  <a-modal
    v-model:visible="isModalOpen2"
    title="Title"
    @ok = "handleOk2"
  >
    <p>{{ modalText2() }}</p>
  </a-modal>
</template>


<script lang="ts" setup>
import html2canvas from 'html2canvas';
import {useAccountStore} from '@/store/account';
// import {useExperimentStore} from '@/store/experiment';
import {message, Modal} from 'ant-design-vue';
import {useRouter} from 'vue-router';
import {h, onMounted, onUnmounted, ref} from 'vue';
import {useStore} from "@/pages/exp4/BOYI/store";


// const principalPrice = ref<number>(0)
const isModalOpen = ref(false)
const modalText = ref("输入错误，请输入对应范围内的值")
const isModalOpen1 = ref(false)
const isModalOpen2 = ref(false)
//const modalText1 = ref("h('div', `您已经被匹配为实验一对手，您的对手是：${mystore.matchUser}，点击确定立刻开始实验`)")
const handleOk = ()=>{
  console.log('ok')
  isModalOpen.value = false
}
const handleOk1 = ()=>{
  console.log('ok')
  isModalOpen1.value = false
}
const handleOk2 = ()=>{
  console.log('ok')
  isModalOpen2.value = false
}

const modalText1 = () => {
  return `您已经被匹配为实验一对手，您的对手是：${mystore.matchUser}，点击确定立刻开始实验`;
};
const modalText2 = () => {
  return `您已经被匹配为实验二对手，您的对手是：${mystore.matchUser1}，点击确定立刻开始实验`;
};
const mystore=useStore()
class WebSocketService {
private socket: WebSocket | null = null;

public connect(url: string): Promise<Event> {
  return new Promise((resolve, reject) => {
    this.socket = new WebSocket(url);
    this.socket.onopen = (event) => {
      this.socket.send("hello");
      console.log('WebSocket Open:', event);
      resolve(event);
    };
    this.socket.onerror = (event) => {
      console.error('WebSocket Error:', event);
      reject(event);
    };
    this.socket.onmessage = (event) => {
      if (!event.data.startsWith("{")) {
        return;
      }
      console.log('WebSocket Message:', JSON.parse(event.data), accountStore.account.index);
      const {data, step} = JSON.parse(event.data)
      //console.log(data)
      //console.log(step)
      const {data1, step1} = JSON.parse(event.data)
      const e = JSON.parse(event.data)
      if (e.event === 'MATCH') {
        //console.log(step)
        mystore.matchInfo = data
      }
      if (e.event === 'MATCH!') {
        //console.log(step1);
        mystore.matchInfo1 = data
      }
      console.log(step);
      if (Number(data.targetId) == accountStore.account.index)
      {
        if (step == 3)
        {
          HardEarn.value=data.HardEarn
        }
        if(step==20){
          NotHard.value=data.NotHard
          principal.value=data.principal
        }
        if (step == 4) {
          console.log('处理接受代理逻辑', data)
          mystore.acceptPrincipal = data.acceptPrincipal
          mystore.principalPrice=data.principalPrice
          mystore.agentPrice=data.agentPrice
          mystore.bug = data.bug
          if (!data.acceptPrincipal) {
            mystore.principalPrice=0
            mystore.agentPrice=0
            message.warning('不接受代理，3s后跳转到实验结束')
            // 不接受代理，3s跳转到实验结束
            setTimeout(() => {
              current.value = 6
            }, 5000)
          }
          return;
        }
        if (step == 5) {
          console.log(step)
          console.log('处理是否努力逻辑', data)
          mystore.bug=data.bug
          mystore.strive = data.strive
          //console.log(strive.value)
          //console.log(bug.value)
          if (mystore.strive == true &&mystore.bug=='高产') {
            mystore.principalPrice =2000-HardEarn.value
            mystore.agentPrice=HardEarn.value-200
          }
          if(mystore.strive==true &&mystore.bug=='低产'){
            mystore.principalPrice=1000-HardEarn.value
            mystore.agentPrice=HardEarn.value-200
          }
          if(mystore.strive==false &&mystore.bug=='低产'){
            mystore.principalPrice=1000-NotHard.value
            mystore.agentPrice=NotHard.value-100
          }
          if(mystore.strive==false &&mystore.bug=='高产'){
            mystore.principalPrice=2000-NotHard.value
            mystore.agentPrice=NotHard.value-100
          }
          // 同步结果
          wsService.send(JSON.stringify({
            step: 10,
            data: {
              targetId: accountStore.account.index == mystore.matchInfo.targetId ? mystore.matchInfo.sourceId : mystore.matchInfo.targetId,
              principalPrice: mystore.principalPrice,
              agentPrice: mystore.agentPrice
            }
          }))
          return;
        }
        if (step == 10) {
          mystore.principalPrice = data.principalPrice
          mystore.agentPrice = data.agentPrice
        }
        if(step==2){
          situation.value = data.situation
        }
        if(step==8){
          price.value = data.price
          low.value=data.low
          high.value=data.high
          mystore.GoodSituationValue=data.GoodSituationValue
          mystore.BadSituationValue=data.BadSituationValue
          //console.log(BadSituationValue.value-price.value)
        }
        if(step==11){
          CustomerValue.value=data.CustomerValue
        }
        if(step==12){
          buy.value=data.buy
          //console.log('处理委托逻辑', data)
          if (data.buy=='false')
          {
            message.warning('买方暂时不买，3s后跳转到实验结束')
            // 不接受委托，3s跳转到实验结束
            setTimeout(() => {
              current0.value = 5
            }, 5000)
          }
          if(data.buy=='2')
          {
            message.warning('买方提出议价策略，即将进行议价')
            // 不接受委托，3s跳转到实验结束
            setTimeout(() => {
              current0.value = 5
            }, 5000)

          }
          if(data.buy=='true')
          {
            message.success('购买成交，3s后跳转到实验结束')
            // 不接受委托，3s跳转到实验结束
            setTimeout(() => {
              current0.value = 6
            }, 5000)
          }
          return;
        }
        if(step==13){
          sale.value=data.sale
          if(data.sale==0){
            message.warning('卖方不提供任何降价策略')
            // 不接受委托，3s跳转到实验结束
            setTimeout(() => {
              current0.value = 6
            }, 5000)
          }
        }
        if(step==19){
          sale.value=data.sale
          if(data.sale==0){
            message.warning('买方不选择购买')
            // 不接受委托，3s跳转到实验结束
            setTimeout(() => {
              current0.value = 6
            }, 5000)
          }
        }
        if(step==14){
          sale.value=data.sale
          buyT.value=data.buyT
          Lowerprice.value=data.Lowerprice
          console.log(Lowerprice)
        }
        if(step==18){
          sale.value=data.sale
          //buyT.value=data.buyT
          Higerprice.value=data.Higerprice
          //console.log(Lowerprice)
        }
        if (e.event === 'MATCH')
        {
          mystore.role = data.target
          mystore.matchUser = data.sourceName
          isModalOpen1.value=true
        }
        if(step==15){
          sale.value=data.sale
          buyT.value=data.buyT
          if (data.buyT==false)
          {
            message.warning('不符合买方预期，买方放弃，3s后跳转到实验结束')
            // 不接受委托，3s跳转到实验结束
            setTimeout(() => {
              current0.value = 6
            }, 5000)
          }
        }
        if(step==16)
        {
          accountStore.role=mystore.role
        }

        if(step==17){
          accountStore.role=mystore.Role
        }
        if (e.event === 'MATCH!')
        {
          mystore.Role = data.target
          mystore.matchUser1 = data.sourceName
          isModalOpen2.value=true
        }

      }

    };
    this.socket.onclose = (event) => {
      console.log('WebSocket Closed:', event);
      clearData()
      clearData1()
    };
  });
}

public send(data: string): void {
  if (this.socket) {
    this.socket.send(data);
  }
}

public close(): void {
  if (this.socket) {
    this.socket.close();
    clearData()
  }
}
}
//实验一的数据
const wsService = new WebSocketService();
// const role = ref('')
const matchInfo = ref()
const accountStore = useAccountStore()
const principal = ref() //是否委托
const strive = ref() //是否努力
let showExperimentTwo=ref(true)


//实验二数据
// const matchUser1=ref('');
// const Role=ref('');
// const matchInfo1 = ref()
const situation=ref();
let disable1=ref<boolean>(false);
let disable2=ref<boolean>(false)
let disable3=ref<boolean>(false)
let disable4=ref<boolean>(false)
let disable5=ref<boolean>(false)
let disable6=ref<boolean>(false)
let disable7=ref<boolean>(false)
let disable8=ref<boolean>(false)
let disable9=ref<boolean>(false)
let disable10=ref<boolean>(false)
let disable11=ref<boolean>(false)
// const BadSituationValue = ref<number>();
const CustomerValue = ref<number>();
// const BadSituationHighPrice=ref<number>();
// const BadSituationLowPrice=ref<number>();
// const GoodSituationValue= ref<number>()
// const GoodSituationHighPrice=ref<number>()
// const GoodSituationLowPrice=ref<number>()
const HardEarn=ref<number>()
const NotHard=ref<number>()
const Lowerprice=ref<number>()
const Higerprice=ref<number>()
const sale=ref<number>()
const price=ref<number>()
let low=ref<boolean>();
let high=ref<boolean>();
const value17 = ref<string>('1');
const buy = ref<string>()
const buyT=ref();
const buyerOffer=ref<number>(0)
const robot=ref<boolean>(false)
function claim(val){
if(buyT.value==false){
  message.warning('您放弃购买，3s后跳转到实验结束')
  // 不接受委托，3s跳转到实验结束
  setTimeout(() => {
    current0.value = 6
  }, 5000)
}
if(buyT.value==true){
  sale.value=2
}
//console.log(val.target.value, buy.value, matchInfo1.value)
wsService.send(JSON.stringify({
  step: 15,
  data: {
    sale:sale.value,
    buyT:buyT.value,
    targetId: accountStore.account.index == mystore.matchInfo1.targetId ? mystore.matchInfo1.sourceId : mystore.matchInfo1.targetId,

  }
}))

}

async function ChooseSituation(){

situation.value = "车况好"
wsService.send(JSON.stringify({
  step: 2,
  data: {
    situation: situation.value,
    targetId: accountStore.account.index == mystore.matchInfo1.targetId ? mystore.matchInfo1.sourceId : mystore.matchInfo1.targetId,

  }
}))
if(robot.value){
  // 移除对不存在的MakebuyerPrice函数的调用
  // 使用固定值或其他逻辑代替
  console.log("人机对战模式 - 车况好");
  CustomerValue.value = 85000; // 使用一个合理的固定值代替
}

}
async function ChooseSituation1(){

situation.value = "车况差"
wsService.send(JSON.stringify({
  step: 2,
  data: {
    situation: situation.value,
    targetId: accountStore.account.index == mystore.matchInfo1.targetId ? mystore.matchInfo1.sourceId : mystore.matchInfo1.targetId,

  }
}))
if(robot.value){
  // 移除对不存在的MakebuyerPrice函数的调用
  // 使用固定值或其他逻辑代替
  console.log("人机对战模式 - 车况差");
  CustomerValue.value = 75000; // 使用一个合理的固定值代替
}
}
function InputLock(){
buyerOffer.value=mystore.BadSituationValue
if(mystore.BadSituationValue >=70000&&mystore.BadSituationValue<=80000){
  disable1.value=true;
}
else {
  isModalOpen.value = true

  mystore.BadSituationValue=0
}
CustomerValue.value=mystore.BadSituationValue
wsService.send(JSON.stringify({
  step: 11,
  data: {
    CustomerValue:CustomerValue.value,
    targetId: accountStore.account.index == mystore.matchInfo1.targetId ? mystore.matchInfo1.sourceId : mystore.matchInfo1.targetId,

  }
}))
}

function InputLock1(){
if(mystore.BadSituationHighPrice>=75000&&mystore.BadSituationHighPrice<=80000)
{
  disable2.value=true;
}
else {
  isModalOpen.value = true

  mystore.BadSituationHighPrice=0
}


}
async function InputLock2(){
if(mystore.BadSituationLowPrice>=70000&&mystore.BadSituationLowPrice<75000)
{
  disable3.value=true;
  let options3 = [mystore.BadSituationLowPrice,mystore.BadSituationHighPrice];
  price.value = mystore.BadSituationHighPrice*0.4+mystore.BadSituationLowPrice*0.6;
  if(price.value==mystore.BadSituationHighPrice){
    high.value=true;
  }
  else{
    low.value=true;
  }
  wsService.send(JSON.stringify({
    step: 8,
    data: {
      price: price.value,
      low:low.value,
      high:high.value,
      targetId: accountStore.account.index == mystore.matchInfo1.targetId ? mystore.matchInfo1.sourceId : mystore.matchInfo1.targetId,

    }
  }))
}
else {
  isModalOpen.value = true

  mystore.BadSituationLowPrice=0
}
if(robot.value){
  // 替换对不存在的BadsituationPrice函数的调用
  console.log("人机对战模式 - 车况差价格判断");
  // 简单模拟AI决策
  const simulatedResponse = { data: { buy: price.value > 77000 ? 'false' : 'true' } };
  console.log(simulatedResponse);
  buy.value = simulatedResponse.data.buy;
  if(buy.value=='2'){
    message.warning('您提出议价策略，即将进行议价')
    // 不接受委托，3s跳转到实验结束
    setTimeout(() => {
      current0.value = 5
    }, 5000)
  }
  if(buy.value=='true'){
    message.success('购买成交，3s后跳转到实验结束')
    // 不接受委托，3s跳转到实验结束
    setTimeout(() => {
      current0.value = 6
    }, 5000)
  }
}
}
function GoodInputLock(){
buyerOffer.value=mystore.GoodSituationValue
console.log(buyerOffer.value)
if(mystore.GoodSituationValue >=70000&&mystore.GoodSituationValue<=90000){
  disable4.value=true;
}
else
{
  isModalOpen.value = true

  mystore.GoodSituationValue=0
}
CustomerValue.value=mystore.GoodSituationValue
wsService.send(JSON.stringify({
  step: 11,
  data: {
    CustomerValue:CustomerValue.value,
    targetId: accountStore.account.index == mystore.matchInfo1.targetId ? mystore.matchInfo1.sourceId : mystore.matchInfo1.targetId,

  }
}))
}
function GoodInputLock1(){
if(mystore.GoodSituationHighPrice>=80000&&mystore.GoodSituationHighPrice<=90000)
{
  disable5.value=true;
}
else {
  isModalOpen.value = true

  mystore.GoodSituationHighPrice=0
}
}
async function GoodInputLock2(){
if(mystore.GoodSituationLowPrice>=70000&&mystore.GoodSituationLowPrice<80000)
{
  disable6.value=true;
  let options3 = [mystore.GoodSituationLowPrice,mystore.GoodSituationHighPrice];
  price.value = mystore.GoodSituationHighPrice*0.6+mystore.GoodSituationLowPrice*0.4;
  if(price.value==mystore.GoodSituationLowPrice){
    low.value=true;
  }
  else{
    high.value==true;
  }
  wsService.send(JSON.stringify({
    step: 8,
    data: {
      price: price.value,
      low:low.value,
      high:high.value,
      GoodSituationValue:mystore.GoodSituationValue,
      BadSituationValue:mystore.BadSituationValue,
      targetId: accountStore.account.index == mystore.matchInfo1.targetId ? mystore.matchInfo1.sourceId : mystore.matchInfo1.targetId,

    }
  }))
}
else {
  isModalOpen.value = true
  mystore.GoodSituationLowPrice=0
}
if(robot.value){
  // 替换对不存在的GoodsituationPrice函数的调用
  console.log("人机对战模式 - 车况好价格判断");
  // 简单模拟AI决策
  const simulatedResponse = { data: { buy: price.value > 85000 ? 'false' : 'true' } };
  console.log(simulatedResponse);
  buy.value = simulatedResponse.data.buy;
  if(buy.value=='2'){
    message.warning('买方提出议价策略，即将进行议价')
    // 不接受委托，3s跳转到实验结束
    setTimeout(() => {
      current0.value = 5
    }, 5000)
  }
  if(buy.value=='true'){
    message.success('购买成交，3s后跳转到实验结束')
    // 不接受委托，3s跳转到实验结束
    setTimeout(() => {
      current0.value = 6
    }, 5000)
  }
}
}
function FixedpriceO(){

disable7.value=true;
console.log(value17.value)

}

async function Buyerchange(val){
console.log(buy.value)
if(buy.value=='false'){
  message.warning('您选择不购买，3s后跳转到实验结束')
  // 不接受委托，3s跳转到实验结束
  setTimeout(() => {
    current0.value = 6
  }, 5000)
}
if(buy.value=='2'){
  message.warning('您提出议价策略，即将进行议价')
  // 不接受委托，3s跳转到实验结束
  setTimeout(() => {
    current0.value = 5
  }, 5000)

}
if(buy.value=='true'){
  message.success('购买成交，3s后跳转到实验结束')
  // 不接受委托，3s跳转到实验结束
  setTimeout(() => {
    current0.value = 6
  }, 5000)
}
console.log(val.target.value, buy.value, mystore.matchInfo1)
wsService.send(JSON.stringify({
  step: 12,
  data: {
    buy:buy.value,
    targetId: accountStore.account.index == mystore.matchInfo1.targetId ? mystore.matchInfo1.sourceId : mystore.matchInfo1.targetId,

  }
}))
if(robot.value)
{
  // 替换对不存在的sendBuyerOffer函数的调用
  console.log("人机对战模式 - 模拟卖家响应");
  // 简单模拟AI决策
  const canSale = price.value > buyerOffer.value * 0.9 && buy.value !== 'false';
  const simulatedResponse = { 
    data: { 
      sale: canSale ? 1 : 0,
      canSale: canSale
    } 
  };
  console.log(simulatedResponse);
  Lowerprice.value = price.value * 0.95; // 模拟降价5%
  
  if(simulatedResponse.data.canSale){
    CustomerValue.value = buyerOffer.value;
    sale.value = 1;
  }
  if(!simulatedResponse.data.canSale)
  {
    sale.value = 0;
  }
}


}

// 是否委托

const Hard=()=>{

if(HardEarn.value>=500&&HardEarn.value<=1000){
  disable10.value=true
  wsService.send(JSON.stringify({
    step: 3,
    data: {
      targetId: accountStore.account.index == mystore.matchInfo.targetId ? mystore.matchInfo.sourceId : mystore.matchInfo.targetId,
      HardEarn: HardEarn.value
    }
  }))
}
else{
  isModalOpen.value = true

  HardEarn.value=0
}
}
const Nothard=()=>{
if(NotHard.value>=100&&NotHard.value<=500){
  disable11.value=true
  principal.value=true
  wsService.send(JSON.stringify({
    step: 20,
    data: {
      targetId: accountStore.account.index == mystore.matchInfo.targetId ? mystore.matchInfo.sourceId : mystore.matchInfo.targetId,
      NotHard: NotHard.value,
      principal:principal.value
    }
  }))
}
//111
else {
  isModalOpen.value = true

  NotHard.value=0
}
}
async function HigerConfirm(){
disable9.value=true
if(!robot.value){

sale.value=undefined
wsService.send(JSON.stringify({
  step: 18,
  data: {
    sale:sale.value,
    Higerprice: Higerprice.value,
    targetId: accountStore.account.index == mystore.matchInfo1.targetId ? mystore.matchInfo1.sourceId : mystore.matchInfo1.targetId,

  }
}))}
if(robot.value){
  // 替换对不存在的sendHigherPrice函数的调用
  console.log("人机对战模式 - 模拟更高价格响应");
  // 简单模拟AI决策
  const simulatedResponse = { 
    data: { 
      sellprice: Higerprice.value * 0.95 // 模拟卖家愿意按95%的价格接受
    } 
  };
  console.log(simulatedResponse);
  Lowerprice.value = simulatedResponse.data.sellprice;
  console.log(Lowerprice.value);
  await new Promise(resolve => setTimeout(resolve, 3000));
  disable9.value = false;
  sale.value = 1;
}
}
function LowerConfirm(){
buyT.value=undefined
disable8.value=true
sale.value=1
wsService.send(JSON.stringify({
  step: 14,
  data: {
    sale:sale.value,
    buyT:sale.value,
    Lowerprice: Lowerprice.value,
    targetId: accountStore.account.index == mystore.matchInfo1.targetId ? mystore.matchInfo1.sourceId : mystore.matchInfo1.targetId,

  }
}))

}
function HigerPrice(){
disable9.value=false
Higerprice.value=0
sale.value=3
}
function Notbuy(){
sale.value=0
wsService.send(JSON.stringify({
  step: 19,
  data: {
    sale:sale.value,
    targetId: accountStore.account.index == mystore.matchInfo1.targetId ? mystore.matchInfo1.sourceId : mystore.matchInfo1.targetId,

  }
}))
message.warning('您不考虑提价，成交失败')
// 不接受委托，3s跳转到实验结束
setTimeout(() => {
  current.value = 6
}, 5000)
}
function LowerPrice(){

Lowerprice.value=0
disable8.value=false
sale.value=1
}
function Notsale(){
sale.value=0
wsService.send(JSON.stringify({
  step: 13,
  data: {
    sale:sale.value,
    targetId: accountStore.account.index == mystore.matchInfo1.targetId ? mystore.matchInfo1.sourceId : mystore.matchInfo1.targetId,

  }
}))
message.warning('您不考虑降价，成交失败')
// 不接受委托，3s跳转到实验结束
setTimeout(() => {
  current.value = 6
}, 5000)
}
function getRandomWeightedOption(options) {
let totalWeight = options.reduce((sum, option) => sum + option.weight, 0);
let randomNum = Math.random() * totalWeight;

for (let option of options) {
  if (randomNum < option.weight) {
    return option.value;
  }
  randomNum -= option.weight;
}
}
//是否接受代理
const acceptChange=(val)=> {
console.log(val.target.value, principal.value, mystore.matchInfo)
let options2 = [
  { value: '高产', weight: 0.7 },
  { value: '低产', weight: 0.3 }
];
mystore.bug = getRandomWeightedOption(options2);
wsService.send(JSON.stringify({
  step: 4,
  data: {
    bug: mystore.bug,
    targetId: accountStore.account.index == mystore.matchInfo.targetId ? mystore.matchInfo.sourceId : mystore.matchInfo.targetId,
    acceptPrincipal: mystore.acceptPrincipal,
    principalPrice:mystore.principalPrice,
    agentPrice:mystore.agentPrice
  }
}))
if (mystore.acceptPrincipal === false) {
  message.warning('不接受代理，3s后跳转到实验结束')
  // 不接受代理，3s跳转到实验结束
  setTimeout(() => {
    current.value = 6
  }, 5000)
}
}

//是否偷懒
const striveChange=(val)=> {
console.log(val.target.value, strive.value, matchInfo.value)
wsService.send(JSON.stringify({
  step: 5,
  data: {
    targetId: accountStore.account.index == mystore.matchInfo.targetId ? mystore.matchInfo.sourceId : mystore.matchInfo.targetId,
    strive: mystore.strive,
    bug:mystore.bug
  }
}))
}
//实验一分配角色
const onGetRole=()=> {
let options = ['代理人', '委托人'];
mystore.role = options[Math.floor(Math.random() * options.length)];
accountStore.role=mystore.role;

}
//实验二分配角色
function onGetRole1(){
let options = ['买方', '卖方'];
mystore.Role = options[Math.floor(Math.random() * options.length)];
accountStore.role=mystore.Role;
wsService.send(JSON.stringify({
  step: 17,
  data: {
    Role: mystore.Role,
    targetId: accountStore.account.index == mystore.matchInfo1.targetId ? mystore.matchInfo1.sourceId : mystore.matchInfo1.targetId,

  }
}))
}
async function CallAI(){
  robot.value=true
  if(mystore.Role=="卖方"){
    // 替换对不存在的getBuyerOffer函数的调用
    console.log("人机对战模式 - 模拟买家匹配");
    // 简单模拟AI决策
    const simulatedResponse = { match: true };
    if(simulatedResponse.match){
      message.success('您已经和人机匹配：AIRobot' )
    }
  }
  if(mystore.Role=="买方"){
    // 替换对不存在的getSellerOffer函数的调用
    console.log("人机对战模式 - 模拟卖家匹配");
    // 简单模拟AI决策
    const simulatedResponse = {
      situation: Math.random() > 0.5 ? '车况好' : '车况差',
      highPrice: Math.floor(Math.random() * 5000) + 75000,
      lowPrice: Math.floor(Math.random() * 5000) + 70000
    };
    console.log(simulatedResponse);
    situation.value = simulatedResponse.situation;
    message.success('您已经和人机匹配：AIRobot' );
    console.log(simulatedResponse);
    if(situation.value=='车况好'){
      mystore.GoodSituationHighPrice = simulatedResponse.highPrice;
      mystore.GoodSituationLowPrice = simulatedResponse.lowPrice;
      price.value = mystore.GoodSituationHighPrice*0.6+mystore.GoodSituationLowPrice*0.4;
    }
    else {
      mystore.BadSituationHighPrice = simulatedResponse.highPrice;
      mystore.BadSituationLowPrice = simulatedResponse.lowPrice;
      price.value = mystore.BadSituationHighPrice*0.4+mystore.BadSituationLowPrice*0.6;
    }
  }
}
//实验一分配对手
async function onGetRival() {
  // 替换对不存在的getMatchUser函数的调用
  console.log("模拟匹配对手");
  // 简单模拟匹配逻辑
  const simulatedResponse = {
    code: 200,
    data: {
      targetName: "随机玩家",
      targetId: Math.floor(Math.random() * 1000),
      sourceId: accountStore.account.index
    }
  };
  
  console.log(simulatedResponse);
  if (simulatedResponse.code == 500) {
    message.error("匹配失败，请稍后再试");
    return;
  }
  message.success('您已经匹配到对手：' + simulatedResponse.data.targetName);
  mystore.matchUser = simulatedResponse.data.targetName;
  mystore.matchInfo = simulatedResponse.data;
  wsService.send(JSON.stringify({
    step: 16,
    data: {
      targetId: accountStore.account.index == mystore.matchInfo.targetId ? mystore.matchInfo.sourceId : mystore.matchInfo.targetId,
    }
  }));
}

//实验二分配对手
async function onGetRival1(){
  // 替换对不存在的getMatchUser1函数的调用
  console.log("模拟匹配对手");
  // 简单模拟匹配逻辑
  const simulatedResponse = {
    code: 200,
    data: {
      targetName: "随机玩家",
      targetId: Math.floor(Math.random() * 1000),
      sourceId: accountStore.account.index
    }
  };
  
  console.log(simulatedResponse);
  if (simulatedResponse.code == 500) {
    message.error("匹配失败，请稍后再试");
    return;
  }
  message.success('您已经匹配到对手：' + simulatedResponse.data.targetName);
  mystore.matchUser1 = simulatedResponse.data.targetName;
  mystore.matchInfo1 = simulatedResponse.data;
  wsService.send(JSON.stringify({
    step: 17,
    data: {
      targetId: accountStore.account.index == mystore.matchInfo1.targetId ? mystore.matchInfo1.sourceId : mystore.matchInfo1.targetId,
    }
  }));
}

onMounted(async () => {
console.log('连接');
await wsService.connect('ws://see-toju.com:8002/api/ws');
})
onUnmounted(async () => {
wsService.close();
})

// 保存实验数据
const clearData = () => {
mystore.role = ''
mystore.matchUser = ''
mystore.matchInfo = undefined
principal.value = undefined
mystore.acceptPrincipal = undefined
mystore.strive = undefined
mystore.principalPrice=0
mystore.agentPrice=0
disable10.value=undefined
disable11.value=undefined
mystore.bug=undefined

}
const clearData1=()=>{
mystore.Role=''
mystore.matchUser1=''
mystore.matchInfo1=undefined
mystore.BadSituationLowPrice=undefined
mystore.BadSituationHighPrice=undefined
mystore.BadSituationValue=undefined
mystore.GoodSituationLowPrice=undefined
mystore.GoodSituationHighPrice=undefined
mystore.GoodSituationValue=undefined;
disable1.value=undefined;
disable2.value=undefined;
disable3.value=undefined;
disable4.value=undefined;
disable5.value=undefined;
disable6.value=undefined;
disable7.value=undefined;
disable8.value=undefined;
}
const saveData=()=>{

}

// 加载动画
const spinning = ref<boolean>(false)
const content1 = ref('')
const content2 = ref('')
const content3 = ref('')
const content4 = ref('')
const content5 = ref('')
const content6 = ref('')
const content7 = ref('')
const current = ref(0)
const router = useRouter();
const current0 = ref(0)
const content11 = ref('')
const content12 = ref('')
const content13 = ref('')
const content14 = ref('')
const content15 = ref('')
const content16 = ref('')
const content17 = ref('')

// 当前实验步骤的游标

const layout = {
labelCol: {
  span: 4,
},
wrapperCol: {
  span: 16,
},
}

const summary = ref('')
const summary1 = ref('')
const content = ref<any>([])
// 根据实际上的实验步数进行修改
const steps = [{
title: '实验原理',
content: '0-content',
},
{
  title: '步骤1',
  content: '1-content',
},
{
  title: '步骤2',
  content: '2-content',
},
{
  title: '步骤3',
  content: '3-content',
},
{
  title: '步骤4',
  content: '4-content',
},
{
  title: '步骤5',
  content: '5-content',
},
{
  title: '步骤6',
  content: '6-content',
},
{
  title: '步骤7',
  content: '7-content',
},
]

const content0 = ref<any>([])
// 根据实际上的实验步数进行修改
const steps1 = [{
title: '实验原理1',
content0: '0-content',
},
{
  title: '步骤1',
  content0: '1-content',
},
{
  title: '步骤2',
  content0: '2-content',
},
{
  title: '步骤3',
  content0: '3-content',
},
{
  title: '步骤4',
  content0: '4-content',
},
{
  title: '步骤5',
  content0: '5-content',
},
{
  title: '步骤6',
  content0: '6-content',
},
{
  title: '步骤7',
  content0: '7-content',
},
]


// 需要图片上传的逻辑
const emit = defineEmits({
  submit: (data) => {
    return true;
  }
});

const sellerget = ref<number>(0);
const buyget = ref<number>(0);
const sellerprofit = ref<number>(0);
const year = ref<number>(0);
const buytrue = ref<string>('');
const buy1 = ref("购买");
const buy2 = ref("不购买");
const buy3 = ref("议价");
const hard = ref<string>('');
const acceptPrincipal = ref<string>('');
const principal1 = ref("接受");
const principal2 = ref("不接受");
const hardearn = ref<number>(0);
const Nothardearn = ref<number>(0);
const buyer = ref<string>('');
const seller = ref<string>('');
const buyerGood = ref("现在有一辆市场二手车价格为90000的雷克萨斯，由于现在这辆车市场很好，您需要给出价格在80000-100000范围内你能接受的价格，一旦确定，不得修改");
const buyerBad = ref("很不幸，当前市场车况较差，这辆雷克萨斯现在的市场价格为75000元，您可以在70000-80000区间内制定这辆在您心中的价值，一旦确定，不得修改");
const sellerGood = ref("您现在需要将市场上价格为90000估价雷克萨斯卖给购买方，但是您想要获利比较多一点，所以决定为这" +
  "辆车制定高价和低价，您可以在80000-90000区间制定低价，90000-100000区间制定高价来获得更高的利润，一旦确定价格，不得修改");
const sellerBad = ref("很不幸，当前由于市场车况较差，您需要将现在市场价格为75000元估价的雷克萨斯卖给买家，当然，您可以选择先将车修理好以高价卖出，也可以选择以低价直接卖出，" +
  "同时，在两年以内的汽车打九折， 两年以上五年以内的汽车打七五折，五年以上十年以内的需要五折出售，十年以上二五折出售，您需要选择的高价区间在75000-8000左右，如果不修理直接卖出，您需要选择的低价区间在70000-75000元之间，一旦选择，不得修改");

const submit = () => {
  const data = {
    // 实验总结
    role: mystore.role,
    summary: mystore.summary,
    matchUser: mystore.matchUser,
    hard: hard.value,
    acceptPrincipal: acceptPrincipal.value,
    hardearn: hardearn.value,
    Nothardearn: Nothardearn.value,
    buyer: buyer.value,
    seller: seller.value,
    bug: mystore.bug,
    agentPrice: mystore.agentPrice,
    principalPrice: mystore.principalPrice,
    Role: mystore.Role,
    matchUser1: mystore.matchUser1,
    situation: situation.value,
    BadSituationHighPrice: mystore.BadSituationHighPrice,
    BadSituationLowPrice: mystore.BadSituationLowPrice,
    BadSituationValue: mystore.BadSituationValue,
    GoodsituationValue: mystore.GoodSituationValue,
    GoodsituationLowprice: mystore.GoodSituationLowPrice,
    GoodsituationHighprice: mystore.GoodSituationHighPrice,
    price: price.value,
    buytrue: buytrue.value,
    lowerprice: Lowerprice.value,
    higerprice: Higerprice.value,
    sellerget: sellerget.value,
    sellerprofit: sellerprofit.value,
    buyget: buyget.value,
    year: year.value,
  };

  if (mystore.role === '代理人') {
    data.hard = mystore.strive;
    if (mystore.acceptPrincipal === 'true') {
      data.acceptPrincipal = principal1.value;
    } else {
      data.acceptPrincipal = principal2.value;
    }
  }

  if (mystore.role === '委托人') {
    data.hardearn = HardEarn.value;
    data.Nothardearn = NotHard.value;
  }

  if (situation.value === '车况好') {
    data.buyer = buyerGood.value;
    data.seller = sellerGood.value;
  }

  if (situation.value === '车况差') {
    data.buyer = buyerBad.value;
    data.seller = sellerBad.value;
  }

  if (value17.value === '1') {
    data.year = 0.9;
  }
  if (value17.value === '2') {
    data.year = 0.75;
  }
  if (value17.value === '3') {
    data.year = 0.5;
  }
  if (value17.value === '4') {
    data.year = 0.25;
  }

  if (buy.value === 'true') {
    data.buytrue = buy1.value;
    data.sellerget = price.value;
    data.buyget = price.value;
    if (situation.value === '车况好') {
      data.sellerprofit = data.price - (data.GoodsituationLowprice + data.GoodsituationHighprice) / 2;
    }
    if (situation.value === '车况差') {
      data.sellerprofit = data.price - ((data.BadSituationHighPrice + data.BadSituationLowPrice) / 2) * data.year;
    }
  }

  if (buy.value === 'false') {
    data.buytrue = buy2.value;
  }

  if (buy.value === '2') {
    data.buytrue = buy3.value;
    data.sellerget = data.lowerprice;
    data.buyget = data.lowerprice;
    if (situation.value === "车况好") {
      data.sellerprofit = data.lowerprice - (data.GoodsituationLowprice + data.GoodsituationHighprice) / 2;
    }
    if (situation.value === "车况差") {
      data.sellerprofit = data.lowerprice - ((data.BadSituationHighPrice + data.BadSituationLowPrice) / 2) * data.year;
    }
  }

  // 通知父组件，并且传参
  emit('submit', data);
};



const next = () => {
if (current.value == 0) {
  content.value.push(content1);
}
if (current.value == 1) {
  content.value.push(content2);
}
if (current.value == 2) {
  content.value.push(content3);
}
if (current.value == 3) {
  content.value.push(content4);
}
if (current.value == 4) {
  content.value.push(content5);
}
if (current.value == 5) {
  content.value.push(content6);
}
if (current.value == 6) {
  content.value.push(content7);
}
current.value++;
}
const prev = () => {
if (current.value == 0) {
  content.value.push(content1);
}
if (current.value == 1) {
  content.value.push(content2);
}
if (current.value == 2) {
  content.value.push(content3);
}
if (current.value == 3) {
  content.value.push(content4);
}
if (current.value == 4) {
  content.value.push(content5);
}
if (current.value == 5) {
  content.value.push(content6);
}
if (current.value == 6) {
  content.value.push(content7);
}
current.value--;
}

const next1 = () => {
if (current0.value == 0) {
  content0.value.push(content11);
}
if (current0.value == 1) {
  content0.value.push(content12);
}
if (current0.value == 2) {
  content0.value.push(content13);
}
if (current0.value == 3) {
  content0.value.push(content14);
}
if (current0.value == 4) {
  content0.value.push(content15);
}
if (current0.value == 5) {
  content0.value.push(content16);
}
if (current0.value == 6) {
  content0.value.push(content17);
}
current0.value++;
}
const prev1 = () => {
if (current0.value == 0) {
  content0.value.push(content11);
}
if (current0.value == 1) {
  content0.value.push(content12);
}
if (current0.value == 2) {
  content0.value.push(content13);
}
if (current0.value == 3) {
  content0.value.push(content14);
}
if (current0.value == 4) {
  content0.value.push(content15);
}
if (current0.value == 5) {
  content0.value.push(content16);
}
if (current0.value == 6) {
  content0.value.push(content17);
}
current0.value--;
}

</script>
<style scoped>
.title {
text-align: center;
font-family: sans-serif;
font-size: 30px;
}

.secondtitle {
text-indent: 2em;
font-weight: bold;
margin-left: 30px;
margin-right: 30px;
}

.content {
text-indent: 2em;
margin-left: 20px;
margin-right: 20px;
}

.guidance {
position: absolute;
right: 50px;
font-weight: bold;
}

.steps-content {
margin-top: 16px;
border: 1px dashed #e9e9e9;
border-radius: 6px;
background-color: #fafafa;
min-height: 200px;
text-align: left;
padding-top: 10px;
}

.image-center {
margin-top: 16px;
border: 1px dashed #e9e9e9;
border-radius: 16px;
background-color: #fafafa;
min-height: 200px;
text-align: center;
padding-top: 10px;
}

.steps-action {
margin-top: 24px;
}

[data-theme='dark'] .steps-content {
background-color: #2f2f2f;
border: 1px dashed #404040;
}

.exp-step-card {
padding-inline: 14px;
}
</style>