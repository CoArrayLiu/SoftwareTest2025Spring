<template>
    <div>
        <a-steps v-model:current="current" size="small">
            <a-step v-for="item in steps" :key="item.title" :title="item.title" />
        </a-steps>
        <div class="steps-content">
            <div class="exp-step-card" v-if="current == 0" ref="content1">
                <h2 style="font-weight: bold">一、实验目的</h2>
                <p class="content">理解软件项目规模度量功能点法原理，通过实验操作掌握功能点法
                <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;学生应以小组为单位，根据本小组"软件工程管理与经济"课程设计项目
                架构及组件等设计成果，以MARK II方法测量该项目的规模(功能点数量)
                <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本实验为课内设计性实验项目，实验学时 1 学时，完成实验报告 1 学时。
                </p>
            </div>
            <div class="exp-step-card" v-if="current == 1" ref="content2">
                <h2 style="font-weight: bold">二、实验原理</h2>
                <h2 key="1" style="font-weight: bold; font-size: 18px;">1. MARK II法测量的范围及目的</h2>
                    <p class="content">功能点指数数量是对软件功能规模的间接定量测量。
                      <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;决定未调整的功能指数的工作包括分析计算实体的数量，分析、标示、归类实体类型，计算输入DET，计算输出DET等。
                      <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;功能点具有两个独立的目标，第一个目标是作为软件测量、对比和分析（例如，软件度量方法）的基础。第二个，也是更重要的目标，是作为软件成本估计模型（例如，公式）
                      和产出工作量（例如，工时）工具的输入，软件成本估计模型和工具则基于功能点和工作量之间的经验成本估计关系（CER），以及把项目数据记录到历史经验库和基准数据库，为将来的项目提供参考和基准数据。                        </p>
                <h2 key="2" style="font-weight: bold; font-size: 18px;"> 2. MARK II法测量的输出 </h2>
                    <p class="content">未调整功能点指数数量。未调整功能点指数数量作为软件成本估计基础，是对软件规模、功能和复杂度的测量。
                      <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如果进行可选的第五步，还需要下列输出：
                      <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.&nbsp;调整因子列表。列出20种调整因子的数值和总调整因子。
                      <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.&nbsp;调整后功能点指数数量。调整后功能点指数数量作为软件成本估计基础，是对软件规模、功能和复杂度的测量。
                    </p>
            </div>
            <div class="exp-step-card" v-if="current == 2" ref="content3">
                <h2 style="font-weight: bold">三、实验步骤</h2>
                <h3 style="font-weight: bold">第一步，决定测量的目的和精确程度</h3>
                <p class="content">首先要标识出客户和计算功能点的目的。例如，它是测量一个特定开发组的工作输出，还是测量一个特定用户所需要的功能。目的是计算需求的所有功能，还是计算交付给用户
                  的功能。
                  <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;此外，测量的时间和所处的项目生命周期阶段，决定了测量的目的和要求的精确程度。
                </p>
            </div>
            <div class="exp-step-card" v-if="current == 3" ref="content4">
                <br/><br/><h3 style="font-weight: bold">第二步，决定测量的边界</h3>
                <p class="content">边界决定了计算所包含的逻辑事务，并且标识了计算可能涉及到的任何界面接口。
                </p>
            </div>
            <div class="exp-step-card" v-if="current == 4" ref="content5">
                <br/><br/><h3 style="font-weight: bold">第三步，决定未调整功能点</h3>
                <p class="content">未调整功能点的数目包括外部输入、外部输出、外部查询、内部逻辑文件和外部接口
                    文件的数目。
                    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    （1）标识逻辑事务
                    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    逻辑事务是应用软件支持的最底层的业务处理过程，每个事务包括3个要素：跨越应用
                    软件边界的输入、一些相关的处理过程、跨越应用软件边界的输出。
                    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    每个逻辑事务是由应用软件外部世界中的独一无二的相关事件或者信息请求触发的。
                    当整个事务完成时，应用软件处于一个与这个事件相关的、自我一致的状态。
                    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    当测量应用软件规模时，一个逻辑事务只能被计算一次，即使它也许可以在应用软件
                    中的多个地方被执行。
                    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    在分析和定义软件需求时，对应用软件（或项目）执行的逻辑事务进行分类，并随项
                    目进行不断的提炼。
                    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    应用软件规模的变更是通过计算逻辑事务的增加、修改和删除的数量，然后汇总得到
                    的。
                    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    在变更逻辑事务的情况下，只需要计算逻辑事务发生变更的那部分规模，而不是逻辑
                    事务全部的规模。变更发生的规模是指对逻辑事务的输入、处理和输出部分的增加、修改
                    和删除次数的汇总。
                    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    （2）对数据实体类型进行标识和分类
                    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    计算引用的实体类型的数量，不要计算它们在一个事务中被使用的次数。
                    计算主要实体，而不计算次要实体。
                    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    建议先进行实体关系分析或者相关数据分析，以生成应用软件的实体图。不要计算物
                    理上存在的文件。
                    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    （3）计算输入和输出DET
                    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    DET是与用户相关的基础性事物，保存着相关信息、实体和它的属性之间的关联关系，
                    它自身也是一个实体。识别和计算输入和输出DET，就是识别一个逻辑事务的每种跨越边
                    界的输入DET，并计算输入有几种类型；识别跨越边界的输出DET，并计算输出有几种类
                    型。
                    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    对以下情况应分别计算：
                    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    1.&nbsp;单个、复合、多用户等数据元素
                    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    2.&nbsp;数组
                    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    3.&nbsp;菜单和事务初始化
                    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    4.&nbsp;事务类型标识
                    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    5.&nbsp;域的标题、页眉、页脚等
                    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    6.&nbsp;物理屏幕限制
                    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    7.&nbsp;在输入/输出流中的粒度和特别处理的DET
                    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    8.&nbsp;不同形式的输入/输出
                    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    分析结果应以文档形式进行保存。
                    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    （4）计算功能规模
                    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    通过计算每个逻辑事务的处理过程中引用的主要实体类型的数量，来测量每个逻辑事
                    务处理过程的规模，汇总所有逻辑事务的功能规模就得到该应用软件的整体规模，是该逻辑事务的输入、处理和输出部分的加权总和。
                    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    工业标准的逻辑事务各部分所占的权重是：输入部分的权重是0.58（每个输入DET），
                    处理部分的权重是1.66（每个实体类型范围），输出部分的权重是0.26（每个输出DET）。
                    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <span style="font-weight: bold; font-style: italic">
                    应用软件的规模＝输入DET数量×0.58＋引用实体数量×1.66＋输出DET数量×1.66
                    </span>
                </p>
                <h3>1. 清点事务</h3>
                <h3 style="text-align: center;">表1：事务清点 </h3>
                <a-table
                    :columns="columns"
                    :data-source="tableData"
                    :pagination="false"
                    bordered
                    size="middle"
                    style="margin-bottom: 10px">
                    <template #bodyCell="{column, text, record}">
                    <template v-if="['name', 'type', 'inputNum', 'outputNum', 'entityNum', 'operation'].includes(column.dataIndex)">
                        <div>
                        <a-input
                            v-if="column.dataIndex === 'name'"
                            style="margin: -5px 0; width: 100%;text-align: center"
                            v-model:value="record.name"
                            @keyup='computeFP(record.index)'
                        />
                        <a-select
                            v-if="column.dataIndex === 'type'"
                            placeholder="请选择"
                            style="margin: -5px 0; width: 100%;text-align: center"
                            v-model:value="record.type"
                            @change='computeFP(record.index)'>
                            <a-select-option value="E" style='text-align: center'>事件</a-select-option>
                            <a-select-option value="Q" style='text-align: center'>查询</a-select-option>
                        </a-select>
                        <a-input
                            v-if="column.dataIndex === 'inputNum'"
                            style="margin: -5px 0; width: 100%;text-align: center"
                            v-model:value="record.inputNum" :maxlength='3'
                            @input="record.inputNum = record.inputNum.replace(/\D/g,'')"
                            @keyup='computeFP(record.index)'
                        />
                        <a-input
                            v-if="column.dataIndex === 'outputNum'"
                            style="margin: -5px 0; width: 100%;text-align: center"
                            v-model:value="record.outputNum" :maxlength='3'
                            @input="record.outputNum = record.outputNum.replace(/\D/g,'')"
                            @keyup='computeFP(record.index)'
                        />
                        <a-input
                            v-if="column.dataIndex === 'entityNum'"
                            style="margin: -5px 0; width: 100%;text-align: center"
                            v-model:value="record.entityNum" :maxlength='3'
                            @input="record.entityNum = record.entityNum.replace(/\D/g,'')"
                            @keyup='computeFP(record.index)'
                        />
                        </div>
                    </template>
                    </template>
                </a-table>
                <a-button type="primary"
                    style="margin-right: 10px;
                    margin-bottom: 10px" @click="handleAdd"
                    :disabled='tableData.length>=20'>添加一行
                </a-button>
                <a-button type="primary"
                    @click="() => {tableData.pop(); computeUnadjusted();}"
                    :disabled='tableData.length<=1'>删除一行
                </a-button>
            </div>
            <div class="exp-step-card" v-if="current == 5" ref="content6">
                <br/><br/><h3 style="font-weight: bold">第四步，计算调整后的功能点指数</h3>
                <p class="content">这一步是非正式步骤，可以省略。建议根据本规程对项目进行分析，以获取对项目整
                体特性的认识。
                <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                （1）计算调整因子
                <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                考虑本实验案例的非功能性，从表 6 采集相对复杂度调整因子(标红数值)， 得到本实验案例的功能点调整因子

                <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                （2）计算调整后功能点指数数量
                <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                将 VAF 数值代入(Mark II 法)功能 点计算公式，计算得到本实验案例的功能点
                </p>
                <p class="content">调整因子说明：Mk
                II功能点分析方法测量的是用户所见应用程序的功能规模。在测量功能规模时，通常还要考虑技术复杂性和特定的质量需求,这些一般称为"非功能性需求"。通常将功能规模与技术复杂度调整系数相乘，得到的结果称为调整后规模。Mk
                II 一般使用19个(<b>具体调整因子见附件</b>)技术特征,每个技术特征以0到5的分值来表示对于应用产生的影响。所有这些技术特征的评分用来计算技术复杂度调整系数。
                基于技术特征的评价得分来计算TCA的计算公式如下:<br/>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>TCA=(TDI * C)+0.65</b><br/>
                式中:<br/>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;TDI——上述19个(或更多)技术特征的得分之和;<br/>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;C——业界平均值是0. 005。<br/>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;TCA可以从0.65到1.125变化(19 个技术特征的评分分别是最小值或最大值)。<br/>
                调整后规模的计算公式如下:<br/>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>AS = FS * TCA</b><br/>
                式中:<br/>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;AS - 调整后规模;<br/>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FS - 功能规模;<br/>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;TCA - 技术复杂度调整系数。<br/>
                </p>
                <a-checkbox @change="onChange" style="margin-left: 15px">
                    使用调整因子
                </a-checkbox>
                <h3 v-show="!checked">2. 计算未调整功能点数</h3>
                <div v-show="!checked" class="unadjusted">
                    合计未调整功能点数：<span style="font-style: italic; font-weight: bold">{{
                    unadjusted
                    }}</span></div>
                <h3 v-show="checked">2. 计算复杂度调整因子</h3>
                <h3 v-show="checked" style="text-align: center;">表2：复杂度调整因子 </h3>
                <a-table v-show="checked" :columns="columnsAdjust2" :pagination="false" :data-source="dataAdjust2" bordered
                        size="middle"
                        style="word-break: break-all;">
                    <template #bodyCell="{ column, record }">
                    <template v-if="column.dataIndex === 'grade'">
                        <a-input-group compact>
                        <a-select v-model:value="record.grade" style="width:100%" @change="computeAdjustedRate">
                            <a-select-option value="0">0</a-select-option>
                            <a-select-option value="1">1</a-select-option>
                            <a-select-option value="2">2</a-select-option>
                            <a-select-option value="3">3</a-select-option>
                            <a-select-option value="4">4</a-select-option>
                            <a-select-option value="5">5</a-select-option>
                        </a-select>
                        </a-input-group>
                    </template>
                    </template>
                </a-table>
                <div v-show="checked" class="unadjusted">（可选）技术复杂度调整系数：<span style="font-weight: bold; font-style: italic">{{
                    adjustedRate
                }}</span></div>
                <h3 v-show="checked">3. 计算调整后功能点数</h3>
                <div v-show="checked" class="unadjusted"> 合计调整后功能点数：<span
                    style="font-weight: bold; font-style: italic">{{ adjusted }}</span></div>
                <h2 style="font-weight: bold">附件</h2>
                <div class="fabtn" v-if="fold_ad===0" @click="fold_appendix">
                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-arrow-bar-down"
                    viewBox="0 0 16 16">
                    <path fill-rule="evenodd"
                        d="M1 3.5a.5.5 0 0 1 .5-.5h13a.5.5 0 0 1 0 1h-13a.5.5 0 0 1-.5-.5zM8 6a.5.5 0 0 1 .5.5v5.793l2.146-2.147a.5.5 0 0 1 .708.708l-3 3a.5.5 0 0 1-.708 0l-3-3a.5.5 0 0 1 .708-.708L7.5 12.293V6.5A.5.5 0 0 1 8 6z"/>
                </svg>
                </div>
                <div class="fabtn" v-if="fold_ad===1" @click="fold_appendix">
                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-arrow-bar-up"
                    viewBox="0 0 16 16">
                    <path fill-rule="evenodd"
                        d="M8 10a.5.5 0 0 0 .5-.5V3.707l2.146 2.147a.5.5 0 0 0 .708-.708l-3-3a.5.5 0 0 0-.708 0l-3 3a.5.5 0 1 0 .708.708L7.5 3.707V9.5a.5.5 0 0 0 .5.5zm-7 2.5a.5.5 0 0 1 .5-.5h13a.5.5 0 0 1 0 1h-13a.5.5 0 0 1-.5-.5z"/>
                </svg>
                </div>
                <div v-if="fold_ad===1">
                <h3 style="text-align: center;">表1：复杂度调整因子</h3>
                <a-table :pagination="false" :data-source="dataAdjust">
                    <a-table-column title="序号" dataIndex="index" key="index" width="8%" align="center"></a-table-column>
                    <a-table-column title="因子名称" dataIndex="name" key="name" width="35%"></a-table-column>
                    <a-table-column title="计分规则" dataIndex="kind" key="kind">
                    <template v-slot="{ text }">
                        <div class="cell-content" v-for="(item,index) in text" :key="item">
                        {{ item }}<br/>
                        </div>
                    </template>
                    </a-table-column>
                </a-table>
                </div>
            </div>
            <div class="exp-step-card" v-if="current == 6" ref="content7">
                <br/><br/><h3 style="font-weight: bold">第五步，分析汇报测量结果 </h3>
                <p class="content"> 测量结果填写到最后实验结果的表中，进行归纳总结，完成实验报告
                </p>
            </div>
            <div class="exp-step-card" v-if="current == 7" ref="content8">
                <h2 style="font-weight: bold">四、实验心得</h2>
                <a-textarea v-model:value=review :autoSize="{ minRows: 7}"
                    style="margin-top: 10px; margin-bottom: 10px"></a-textarea>
            </div>

        </div>
        <div class="steps-action">
            <a-button v-if="current < steps.length - 1" type="primary" style="float: right;"
                @click="next"><step-forward-outlined />下一步</a-button>

              
              <a-button v-if="current == steps.length - 1" type="primary" style="float: right; margin-left: 7px" @click="submit">
                提交
              </a-button>

              <a-button v-if="current == steps.length - 1" type="primary" style="float: right;" class="btn" @click="downLoadFile">
                <EyeOutlined />
                预览报告
              </a-button>

            <a-button v-if="current > 0" style="margin-left: 8px" @click="prev"><step-backward-outlined />上一步</a-button>
        </div>
    </div>
    <div v-if="current > 0" style="margin-top: 20px; display: flex;justify-content: flex-end;">
        <a-button  @click="clearData" >清空数据</a-button>
    </div>
</template>


<script lang="ts">
//import { message } from 'ant-design-vue';
import { emit } from 'process';
//import { useAccountStore, useExperimentStore } from '@/store';

//const experimentStore = useExperimentStore();
//const accountStore = useAccountStore();

export default {
    data() {
        return {
            // 项目测量报告参数
            current: 0,
            message,
            fold_ad: 0,
            unfold: 1,

            columnsAdjust: [
                {align: 'center', title: '序号', dataIndex: 'index',},
                {align: 'center', title: '统计值', dataIndex: 'name',},
                {align: 'center', title: '计算方式', dataIndex: 'kind',},
            ],
            columnsAdjust2: [
                {
                title: '序号', dataIndex: 'index', key: 'component',
                align: 'center', width: "8%"
                },
                {
                title: '因子', dataIndex: 'title', key: 'component',
                align: 'center', width: "80%"
                },
                {
                title: '等级', dataIndex: 'grade', key: 'component',
                align: 'center'
                },
            ],
            dataAdjust: [
                {
                index: 1, name: "数据通信——使用通信设施传输数据和控制信息",
                kind: [
                    "0: 单纯的批处理或单独的个人计算机\n", "1: 有远程数据录入或打印的批处理\n", "2: 有远程数据录入和打印的批处理\n",
                    "3: 在线数据收集的事务处理前台终端\n", "4: 多于一个前台终端，但是应用只支持一种类型的事务处理通信协议\n", "5: 多于一个前台终端，应用支持多种类型的事务处理通信协议\n"
                ]
                },
                {
                index: 2, name: "分布式功能——应用分布在两个或更多的处理器上执行",
                kind: [
                    "0: 在应用程序的组件之间不进行数据或处理功能的调度", '1: 应用程序为最终用户在应用的另一个组件上进行处理而提供数据', '2: 应用程序为在另一台机器上进行处理(不是最终用户)而提供数据',
                    '3: 分布式处理，实时在线，数据传输是单向的', '4: 分布式处理，实时在线，数据传输有双向的', '5: 处理功能动态地在最合适的应用程序组件上执行',
                ]
                },
                {
                index: 3, name: "性能——应用程序的响应/吞吐率",
                kind: [
                    '0: 用户没有提出性能需求', '1: 用户提出并评审了性能要求，但无需采取特别的处理', '2: 用户提出高峰时段在线响应性能要求，但是对于CPU使用无需进行特别设计',
                    '3: 用户提出整个业务工作日在线响应性能要求，但是对于CPU使用无需特别设计', '4: 提出的性能要求需要在设计时进行专门的性能分析', '5: 为了满足用户提出的性能要求，在设计，开发和或安装阶段使用了性能分析工具',
                ]
                },
                {
                index: 4, name: "超负荷使用配置——运行应用的设备已经超负荷",
                kind: [
                    "0~3 :典型的应用运行在标准生产机器上，无操作限制要求\n", "4: 操作限制要求对在(单个)中央处理器上运行的应用施加特别的约束限制\n",
                    "5: 在此基础之上，还要对应用的分布式组件施加特别的约束限制\n"
                ]
                },
                {
                index: 5, name: "事务到达率一高事务到达率会 引发除性能特征以外的其他问题",
                kind: [
                    "0: 没有高峰事务时段\n", "1: 高峰时段影响10%的事务\n", "2: 高峰时段影响50%的事务\n",
                    "3: 高峰时段影响所有事务\n", "4: 在设计阶段进行性能分析\n", "5: 在设计、开发和安装时使用性能分析工具\n"
                ]
                },
                {
                index: 6, name: "在线数据录入——用于输入的终端设备",
                kind: [
                    "0: 所有的事务都是批处理(无数据录入)\n", "1: 1%~7%的事务是交互式数据录入\n", "2: 8%~15%的事务是交互式数据录入\n",
                    "3: 16%~23%的事务是交互式数据录入\n", "4: 24%~30%的事务是交互式数据录入\n", "5: 超过30%的事务是交互式数据录入\n"
                ]
                },
                {
                index: 7, name: "为终端用户使用效率进行设计—在设计中考虑人的因素",
                kind: [
                    "用户要求在线功能来帮助应用的使用，包括:\n" +
                    "导航帮助;菜单;在线帮助;自动光标移动;屏幕滚动;远程打印;预先分配的功能键;\n" +
                    "在线事务的批处理任务;屏幕数据的光标选择;显示器的频繁使用(颜色，高亮，等等);\n" +
                    "在线事务的硬拷贝;鼠标;窗体;最小化屏幕;双语种;多语种。\n",
                    "0: 不含.上述任何条目\n",
                    "1: 含上述条目中的1~3条\n",
                    "2: 含上述条目中的4~5条\n",
                    "3: 含上述条目中的大于5条\n",
                    "4: 除上述条目之外，还有其他人的因素所需的设计任务\n",
                    "5: 除上述条目之外，还需要原型或特别工具来展现特别的人的因素的设计目标是否得到满足\n"
                ]
                },
                {
                index: 8, name: "在线更新—数据实时更新",
                kind: [
                    "0: 没有实时数据更新\n",
                    "1~2: 控制文件的在线更新，更新数据量小，恢复容易\n",
                    "3: 主要逻辑内部文件在线更新\n",
                    "4: 在此基础之上，保护以免数据丢失是必须的\n",
                    "5: 在此基础之上，海量数据导致对于恢复过程的成本考虑\n"
                ]
                },
                {
                index: 9, name: "处理的复杂性—除了简单实体数据转换之外的内部处理",
                kind: [
                    "下述情况属于内部复杂处理:\n" +
                    "敏感控制(例如特别的审计过程)和/或应用特定的安全处理;扩展的逻辑处理;\n" +
                    "扩展的数学处理;大量异常处理，大量未完成的事务，大量需要重新处理的事务;\n" +
                    "应对 可能存在的多种输入/输出方式的复杂处理，例如:多媒体设备。\n",
                    "计分规则如下:\n" +
                    "0: 上述条目中没有适用的\n", "1: 上述条目中有1.个适用\n", "2: 上述条目中有2个适用\n",
                    "3: 上述条目中有3个适用\n", "4: 上述条目中有 4个适用\n", "5: 上述条目全部适用\n"
                ]
                },
                {
                index: 10, name: "代码复用性—代码被设计来与其他应用共享或重复使用",
                kind: [
                    "0: 没有可复用的代码\n", "1: 在应用范围内复用代码\n", "2: 小于10%的应用考虑复用代码\n", "3: 小于10%的应用考虑复用代码\n",
                    "4: 为了实现代码复用，需要进行特别的打包和/或文档，在源代码级别进行定制化\n", "5: 为了实现代码复用，需要进行特别的打包和/或文档，通过参数方式来进行定制化\n"
                ]
                },
                {
                index: 11, name: "易安装—数据转换和易安装需要在设计时考虑",
                kind: [
                    "0: 用户没有提出特别的转换和安装要求\n", "1: 用户没有提出特别的转换和安装要求，但是安装需要特别的设置\n",
                    "2: 用户表提出转换和安装要求，且提供并测试了安装指南\n", "3: 在此基础之上，易安装性要求的影响被认为是重要的\n",
                    "4: 在评分为2分的基础之上，还提供并测试了转换和安装工具\n", "5: 在评分为3分的基础之上，还提供并测试了转换和安装工具\n"
                ]
                },
                {
                index: 12, name: "操作易用性—在设计时考虑方便的操作",
                kind: [
                    "0: 客户没有提出特别的操作易用性需求\n",
                    "1~4: 从如下选择，每项有1分，除非另行说明\n",
                    "a)用户提出应用程序所特有的启动, 备份和恢复过程操作需求,操作过程需要操作员的介入;\n",
                    "b) 同上，但操作过程不需要操作员的介入(此项2分) ;\n",
                    "c)应用程序最 小化磁带加载的需要;\n",
                    "d)应用程序最 小化纸张处理的需要。\n",
                    "5: 应用程序为无人值守进行设计,除了启动和关闭操作之外,无需操作员介入,错误恢复也是自动完成的。\n"
                ]
                },
                {
                index: 13, name: "多节点—应用程序在多个节点和/或多个组织中使用",
                kind: [
                    "0: 没有考虑多用户节点的需求\n", "1: 在设计中考虑到多节点需求，但是使用的是相同的硬件和软件\n",
                    "2: 在设计中考虑到多节点需求，但是使用了相似的硬件和软件\n", "3: 在设计中考虑到多节点需求，并且为满足多节点需求使用了不一样的硬件和软件\n",
                    "如果符合下面的条目，每项加1分:\n",
                    "a)为支持多 节点的应用程序提供了文档和支持计划，并且经过测试;\n",
                    "b)节点部署 在不同的国家。\n"
                ]
                },
                {
                index: 14, name: "支持变化—在设计时考虑支持未来的变化",
                kind: [
                    "0: 没有特别的用户需求来要求设计能够支持变化的应用程序\n", "1: 提供灵活的查询能力，能够处理简单逻辑查询\n",
                    "2: 提供灵活的查询能力，能够处理- -般逻辑查询\n", "3: 提供灵活的查询能力，能够处理复杂逻辑查询\n",
                    "加上以下得分:\n",
                    "a)如果重要的控制数据保存 在表格中，且这些表格的维护是通过在迟些时候发生变化的在线交互式过程完成的:\n",
                    "b)如果重要 的控制数据保存在表格中，且这些表格的维护是在线交互式更新。\n"
                ]
                },
                {
                index: 15, name: "其他应用程序的需求—接口",
                kind: [
                    "0: 应用程序完全独立运行\n", "1~5: 必须与其他应用程序同步接口或数据共享的需求，每个应用程序加1分，最多5分\n"
                ]
                },
                {
                index: 16, name: "安全、隐私、可审计—保密/安全专有特性",
                kind: [
                    "1: 如果应用程序必须满足符合相关法律法规的个人隐私需求\n",
                    "1: 如果应用程序必须满足特殊的可审计需求\n",
                    "2: 如果应用程序必须满足异常情况下的安全性需求\n",
                    "1: 如果应用程序必须进行数据通信加密\n"
                ]
                },
                {
                index: 17, name: "用户培训需要—特定需求",
                kind: [
                    "0: 如果没有专门的培训材料或课程需要开发\n", "1: 提供标准的教程帮助\n", "2: 提供超链接形式的教程帮助\n",
                    "3: 提供培训课程材料\n", "4: 提供在线培训课程材料\n", "5: 提供专用于培训的独立完整的应用程序或模拟器\n"
                ]
                },
                {
                index: 18, name: "第三方直接使用—应用程序的使用/关联度",
                kind: [
                    "0: 应用程序没有与第三方关联\n", "1: 数据发送给或接收自已知的第三方\n", "2: 已知的第三方以只读模式直接接入应用程序\n",
                    "3: 已知的第三方直接接入应用程序，可以在线更新\n", "4: 已知的第三方直接接入应用程序，可以在线创建、更新和删除\n", "5: 未知的第三方可以访问应用程序\n"
                ]
                },
                {
                index: 19, name: "文档化",
                kind: [
                    "统计如下每个文档类型，这些文档在项目结束时需要交付并保持及时更新:\n",
                    "功能规格说明书(过程和数据);技术规格说明书;程序文档(至少流程图);\n" +
                    "数据元素库;数据元素/记录/程序的交叉引用;用户手册;操作手册;\n" +
                    "应用概览或简介;测试数据库;用户培训课程材料;应用成本/收益跟踪文档;变更请求/错误报告日志。\n",
                    "0: 0~2个文档类型\n", "1: 3~4个文档类型\n", "2: 5~6个文档类型\n",
                    "3: 7~8个文档类型\n", "4: 9~10个文档类型\n", "5: 11~12个文档类型\n"
                ]
                },
            ],
            columns: [
                {
                title: '编号',
                dataIndex: 'index',
                scopedSlots: {customRender: 'index'},
                width: "10%"
                },
                {
                title: '事务名称',
                dataIndex: 'name',
                scopedSlots: {customRender: 'name'},
                width: "18%"
                },
                {
                title: '事件或查询',
                dataIndex: 'type',
                scopedSlots: {customRender: 'type'},
                width: "15%"
                },
                {
                title: '输入DET数量',
                dataIndex: 'inputNum',
                scopedSlots: {customRender: 'input'},
                width: "15%"
                },
                {
                title: '输出DET数量',
                dataIndex: 'outputNum',
                scopedSlots: {customRender: 'output'},
                width: "15%"
                },
                {
                title: '引用实体数量',
                dataIndex: 'entityNum',
                scopedSlots: {customRender: 'entity'},
                width: "15%"
                },
                {
                title: '功能点指数',
                dataIndex: 'FP',
                scopedSlots: {customRender: 'FP'},
                width: "13%"
                },
            ],
            steps: [{
                title: '实验目的',
                content: '0-content',
            }, {
                title: '实验原理',
                content: 'First-content',
            }, {
                title: '决定测量的目的和精确程度',
                content: 'Second-content',
            }, {
                title: '决定测量的边界',
                content: 'Third-content',
            },
            {
                title: '决定未调整功能点',
                content: 'Fourth-content',
            },
            {
                title: '计算调整后的功能点指数',
                content: 'Fifth-content',
            },
            {
                title: '分析汇报测量结果',
                content: 'Sixth-content',
            },
            {
                title: '实验心得',
                content: 'Seventh-content',
            }],

        }
    },
    methods: {
        fold_appendix() {
            if (this.fold_ad === 0) {
                this.fold_ad = 1;
            } else {
                this.fold_ad = 0;
            }
        },
        showDrawer() {
            this.visible = true;
            const content_2 = this.$refs.content_2;
            this.content.push(content_2);
        },
        next() {
            this.current++;
        },
        prev() {
            this.current--;
        },

    },

}
</script>

<script setup lang = 'ts'>
import { useDataStore } from './stores';
import { storeToRefs } from 'pinia';
import { message } from 'ant-design-vue';
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useExperimentStore } from '@/store/experiment';
import { useAccountStore } from '@/store/account';

const dataStore = useDataStore();
const data0 = storeToRefs(dataStore);
const review = data0.review;
const tableData = data0.tableData;
const unadjusted = data0.unadjusted;
const adjusted = data0.adjusted;
const adjustedRate = data0.adjustedRate;
const checked = data0.checked;
const dataAdjust2 = data0.dataAdjust2;

const router = useRouter();
const experimentStore = useExperimentStore();
const accountStore = useAccountStore();
const current = ref(0);
const spinning = ref(false);
const receivedData = ref(null);
const experimentDate = new Date();

// 添加onMounted生命周期钩子用于恢复页面状态
onMounted(() => {
  // 尝试从会话存储中恢复页面状态
  try {
    const savedState = sessionStorage.getItem('markii_page_state');
    if (savedState) {
      const state = JSON.parse(savedState);
      // 恢复当前步骤
      if (state.current) {
        current.value = parseInt(state.current);
      }
      console.log('状态已恢复:', state);
    }
  } catch (e) {
    console.error('恢复页面状态失败:', e);
  }

  const savedData1 = localStorage.getItem('experimentData');
  if (savedData1) {
    receivedData.value = JSON.parse(savedData1);
  }
});

async function downLoadFile() {
  // 先准备基础数据
  const finalData = {
    experiment_id: "14",
    reporter_id: accountStore.account.email.slice(0, accountStore.account.email.indexOf('@')),
    reporter_name: accountStore.account.name,
    course_name: accountStore.course.courseName,
    course_id: accountStore.course.courseId,
    submit_time: experimentDate.getTime(),
  };

  // 如果没有先提交数据，先执行提交操作
    
    // 如果提交后仍然没有数据，显示错误
    if (!receivedData.value) {
      message.error('报告数据未准备好，请先提交报告数据。');
      return;
    }
  
  
  // 使用从子组件接收的数据来填充 reportData
  const reportData = {
    experiment_id: 14,  // 确保ID一致，使用数字格式
    course_id: accountStore.course.courseId,
    ...finalData,
    ...receivedData.value,
  };

  // 在跳转前保存当前页面状态到 sessionStorage
  try {
    sessionStorage.setItem('markii_page_state', JSON.stringify({
      current: current.value.toString(),
      hasSubmitted: true
    }));
  } catch (e) {
    console.error('保存页面状态失败', e);
  }

  console.log('即将发送报告数据:', reportData);

  spinning.value = true;
  try {
    // 路由跳转到报告预览页面，传递报告数据参数
    router.push({
      path: '/report-preview',
      query: {
        reportData: encodeURIComponent(JSON.stringify(reportData))
      }
    });
  } catch (error) {
    console.error('跳转到报告预览页面失败:', error);
    message.error('预览报告失败，请稍后重试');
  } finally {
    spinning.value = false;
  }
}

function handleAdd() {
  let len = tableData.value.length;
  let index = 'T' + (len >= 8 ? '' : '0') + (len + 1);
//   console.log(index);
  const newData = {
    index: index,
    name: '',
    type: undefined,
    inputNum: '',
    outputNum: '',
    entityNum: '',
    FP: ''
  };
  tableData.value.push(newData);
}

function computeFP(index) {
  index = parseInt(index.substring(1)) - 1
  if (tableData.value[index].inputNum === '') {
    tableData.value[index].FP = ''
    return
  }
  if (tableData.value[index].outputNum === '') {
    tableData.value[index].FP = ''
    return
  }
  if (tableData.value[index].entityNum === '') {
    tableData.value[index].FP = ''
    return
  }
  let fp = parseInt(tableData.value[index].inputNum) * 0.58
    + parseInt(tableData.value[index].entityNum) * 1.66
    + parseInt(tableData.value[index].outputNum) * 0.26
  tableData.value[index].FP = fp.toFixed(2)
  adjusted.value = unadjusted.value * adjustedRate.value
  computeUnadjusted();
}
function computeUnadjusted() {
  unadjusted.value = 0.0;
  for (let i = 0; i < tableData.value.length; i++) {
    unadjusted.value += parseFloat(tableData.value[i].FP);
  }
  unadjusted.value = parseFloat(unadjusted.value.toFixed(2));
}
function computeAdjustedRate() {
  let rate = 0;
  for (let i = 0; i < dataAdjust2.value.length; i++) {
    rate += parseInt(dataAdjust2.value[i].grade);
  }
  adjustedRate.value = rate * 0.005 + 0.65
  adjustedRate.value = parseFloat(adjustedRate.value.toFixed(3));
  adjusted.value = unadjusted.value * adjustedRate.value
  adjusted.value = parseFloat(adjusted.value.toFixed(2));
}

function onChange(e) {
  checked.value = !checked.value
//   console.log(`checked = ${e.target.checked}`);
}

function clearData() {
    review.value = '';
    while(tableData.value.length > 0) {
        tableData.value.pop();
    }
    for (let i = 0; i < dataAdjust2.value.length; i++) {
        dataAdjust2.value[i].grade = "0";
    }
    adjustedRate.value = 0
    adjusted.value = 0
    unadjusted.value = 0;
}
const emit = defineEmits({
  submit: (data) => {
    return true
  }
});
const submit = () => {
    const data = {
        summary: review.value,
        tableData:tableData.value,
        checked:checked.value,
        unadjusted:unadjusted.value,
        dataAdjust2:dataAdjust2.value,
        adjustedRate:adjustedRate.value,
        adjusted:adjusted.value
    };
  localStorage.setItem('experimentData', JSON.stringify(data));
  receivedData.value = data;

  // console.log(JSON.stringify(data));
  // 此处使用 emit，确保在 setup 中定义了 emit
  emit('submit', data);
};
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