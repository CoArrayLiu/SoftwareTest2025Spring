<template>
    <div>
        <!-- 第一层导引按钮 -->
        <a-steps v-model:current="mainStep" size="small">
            <a-step v-for="item in mainSteps" :key="item.title" :title="item.title" />
        </a-steps>

        <!-- 当选中实验步骤时显示第二层导引按钮 -->
        <div v-if="mainStep === 3" class="sub-steps">
            <a-steps v-model:current="subStep" size="small" direction="horizontal" @change="handleStepChange">
                <a-step v-for="item in subSteps" :key="item.title" :title="item.title" :disabled="true" />
            </a-steps>
        </div>

        <div class="steps-content">
            <!-- 实验目的 -->
            <div class="exp-step-card" v-if="mainStep === 0">
                <h2 style="text-align: left;">实验目的</h2>
                <p style="margin-bottom: 20px; text-align: left; text-indent: 2em;">
                    本实验为课内设计性实验项目，实验学时1学时，完成实验报告1学时。
                </p>
                <p class="content" style="text-align: left;">
                    理解软件项目规模度量功能点法原理，通过实验操作掌握功能点法。
                    学生应以小组为单位，根据本小组"软件工程管理与经济"课程设计项目架构及组件等设计成果，以功能点方法测量该项目的规模(功能点数量)。
                    建议选用某一种功能点方法度量课程设计项目的功能点，并采用另外一种功能点方法或其他的软件规模度量方法对前一种方法的度量结果进行验证。
                </p>
                <p style="text-align: left;">实验应达到以下目的：</p>
                <ul style="text-align: left;">
                    <li>理解COSMIC方法的基本概念和原理，掌握如何通过COSMIC方法测量软件的功能规模。</li>
                    <li>通过实践操作，让学生学会如何将COSMIC方法应用于实际的软件规模测量中。</li>
                    <li>培养学生的分析和评估能力，通过对软件功能规模的测量，理解软件结构和功能。</li>
                </ul>
            </div>

            <!-- 实验原理 -->
            <div class="exp-step-card" v-if="mainStep === 1">
                <h2 style="text-align: left;">实验原理</h2>
                <div style="padding: 0; background: none; box-shadow: none;">
                    <h3 style="text-align: left;">1. COSMIC方法概述</h3>
                    <p class="content" style="text-align: left;">COSMIC（Common Software Measurement International Consortium）方法是一种国际标准化的软件功能规模度量方法。该方法基于软件功能用户需求（FUR）中的数据移动来测量软件的功能规模，具有科学性、客观性和可重复性的特点。</p>
                    <h3 style="text-align: left;">2. 功能规模测量原理</h3>
                    <p class="content" style="text-align: left;">功能规模测量是量化软件功能规模的过程。在COSMIC方法中，通过以下步骤实现测量：</p>
                    <ul style="text-align: left;">
                        <li>识别软件边界和功能用户</li>
                        <li>识别功能处理过程</li>
                        <li>识别数据组和数据属性</li>
                        <li>识别和计数数据移动</li>
                    </ul>
                    <h3 style="text-align: left;">3. 数据移动类型</h3>
                    <p class="content" style="text-align: left;">COSMIC方法定义了四种基本的数据移动类型：</p>
                    <ul style="text-align: left;">
                        <li>输入（Entry）：将数据从功能用户移动到功能处理过程中</li>
                        <li>输出（Exit）：将数据从功能处理过程移动到功能用户</li>
                        <li>读取（Read）：从持久存储中读取数据组</li>
                        <li>写入（Write）：将数据组写入持久存储</li>
                    </ul>
                    <h3 style="text-align: left;">4. 度量单位</h3>
                    <p class="content" style="text-align: left;">COSMIC方法使用CFP（COSMIC Function Point）作为度量单位。每个数据移动计为1 CFP，软件的总功能规模为所有数据移动的CFP之和。</p>
                    <h4 style="text-align: left;">注意事项：</h4>
                    <ul style="text-align: left;">
                        <li>准确识别功能用户系统边界</li>
                        <li>正确划分功能处理过程</li>
                        <li>完整识别数据移动</li>
                        <li>合理计算最终的功能点数</li>
                    </ul>
                </div>
            </div>

            <!-- 实验方法 -->
            <div class="exp-step-card" v-if="mainStep === 2">
                <h2>实验方法</h2>
                <div class="method-content">
                    <h3>COSMIC功能点测量方法步骤</h3>

                    <div class="method-step">
                        <h4>1. 确定测量目标</h4>
                        <p class="content">
                            明确测量的目的、范围和精度要求。确定待测软件的边界，识别功能用户类型。
                        </p>
                    </div>

                    <div class="method-step">
                        <h4>2. 识别功能用户</h4>
                        <p class="content">
                            识别与软件交互的人员、硬件设备或其他软件系统。功能用户是软件的数据发送者和/或接收者。
                        </p>
                        <div class="example-box">
                            <h5>示例：</h5>
                            <ul>
                                <li>人类用户（如系统操作员）</li>
                                <li>外部硬件设备（如传感器）</li>
                                <li>其他软件应用（如接口系统）</li>
                            </ul>
                        </div>
                    </div>

                    <div class="method-step">
                        <h4>3. 识别功能过程</h4>
                        <p class="content">
                            识别由功能用户触发的、完整的、独立的基本功能单元。每个功能过程必须是原子的，并在执行完成后使软件达到一个稳定状态。
                        </p>
                        <div class="example-box">
                            <h5>功能过程特征：</h5>
                            <ul>
                                <li>由功能用户触发</li>
                                <li>功能完整且独立</li>
                                <li>执行后达到稳定状态</li>
                                <li>包含至少两个数据移动</li>
                            </ul>
                        </div>
                    </div>

                    <div class="method-step">
                        <h4>4. 识别数据组</h4>
                        <p class="content">
                            识别在功能过程中移动的数据属性的唯一、非空、无序、无重复的集合。每个数据组代表一个唯一的对象。
                        </p>
                        <div class="tip-box">
                            <h5>提示：</h5>
                            <p>数据组通常对应于：</p>
                            <ul>
                                <li>实体关系模型中的实体</li>
                                <li>类图中的类</li>
                                <li>数据库中的表</li>
                            </ul>
                        </div>
                    </div>

                    <div class="method-step">
                        <h4>5. 识别数据移动</h4>
                        <p class="content">
                            在每个功能过程中识别数据移动。COSMIC定义了四种基本的数据移动类型：
                        </p>
                        <div class="movement-types-grid">
                            <div class="movement-type-card">
                                <h5>输入 (Entry)</h5>
                                <p>将数据从功能用户移动到功能过程中</p>
                            </div>
                            <div class="movement-type-card">
                                <h5>输出 (Exit)</h5>
                                <p>将数据从功能过程移动到功能用户</p>
                            </div>
                            <div class="movement-type-card">
                                <h5>读取 (Read)</h5>
                                <p>从持久存储中读取数据组</p>
                            </div>
                            <div class="movement-type-card">
                                <h5>写入 (Write)</h5>
                                <p>将数据组写入持久存储</p>
                            </div>
                        </div>
                    </div>

                    <div class="method-step">
                        <h4>6. 计算功能规模</h4>
                        <p class="content">
                            每个数据移动计为1 CFP（COSMIC Function Point）。软件的总功能规模为所有数据移动的CFP之和。
                        </p>
                        <div class="formula-box">
                            <p>总功能规模 = Σ(输入 + 输出 + 读取 + 写入)</p>
                        </div>
                    </div>

                    <div class="method-note">
                        <h4>注意事项：</h4>
                        <ul>
                            <li>确保识别的功能用户和功能过程完整且准确</li>
                            <li>避免重复计数数据移动</li>
                            <li>保持数据组的唯一性</li>
                            <li>记录测量过程中的假设和决策</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <!-- 实验步骤 -->
        <div class="exp-step-card" v-if="mainStep === 3">
            <!-- 确定测量目标 -->
            <div v-if="subStep === 0">
                <h2>确定测量目标</h2>
                <p class="content">
                    在进行实际度量前，我们必须精准确定五个关键的测量策略参数。测量目的是核心指引，它决定着后续所有参数的设定方向，比如是为了估算软件开发成本、评估软件功能规模，还是衡量软件性能基准等。待测量的软件块规模，需明确项目中哪些软件块应纳入度量范畴，哪些予以排除。软件块的分解级别有多种，像整个应用、子系统、模块或组件等不同层次，这有助于细化度量粒度。每个待测量软件块的功能用户，包括人（如系统管理员、普通用户
                    ）或物（如硬件设备、其他软件系统
                    ），他们是被测软件的数据发送者或接收者，其感知到的功能是度量对象。软件在所处软件架构中的层次，例如表示层、业务逻辑层、数据访问层等，需将待测软件限定在某一层次内，以便准确开展度量工作 。
                    这些参数相互关联，共同为后续的软件规模度量奠定基础。</p>
            </div>

            <!-- 识别功能用户 -->
            <div v-if="subStep === 1">
                <h2>识别功能用户</h2>
                <p class="content">请识别系统中的功能用户（包括人或物（硬件设备或其他软件系统），它们是被测软件的数据发送者或接收者）</p>

                <a-table :columns="functionalUserColumns" :dataSource="functionalUsers" :pagination="false">
                    <!-- 原有的表格模板容 -->
                    <template #bodyCell="{ column, record, index }">
                        <template v-if="column.key === 'index'">
                            {{ index + 1 }}
                        </template>
                        <template v-if="column.key === 'name'">
                            <div v-if="editingKey === record.key">
                                <a-input v-model:value="record.name" @pressEnter="handleSave(record.key)" />
                            </div>
                            <div v-else>
                                {{ record.name }}
                            </div>
                        </template>
                        <template v-if="column.key === 'action'">
                            <div class="action-buttons">
                                <template v-if="editingKey === record.key">
                                    <a @click="handleSave(record.key)">保存</a>
                                </template>
                                <template v-else>
                                    <a @click="handleEdit(record)">编辑</a>
                                </template>
                                <a-divider type="vertical" />
                                <a-popconfirm title="确定要删除这行吗?" @confirm="handleDelete(record.key)">
                                    <a>删除</a>
                                </a-popconfirm>
                            </div>
                        </template>
                    </template>
                </a-table>

                <div style="margin-top: 16px">
                    <a-button type="primary" @click="handleAdd">添加功能用户</a-button>
                </div>
            </div>

            <!-- 识别功能过程 -->
            <div v-if="subStep === 2">
                <h2>识别功能过程</h2>
                <p class="content">请识别系统中的功能过程（功能过程是由功能用户触发的、完整的、独立的、基本功能单元）</p>

                <a-table :columns="functionalProcessColumns" :dataSource="functionalProcesses" :pagination="false">
                    <template #bodyCell="{ column, record, index }">
                        <template v-if="column.key === 'index'">
                            {{ index + 1 }}
                        </template>
                        <template v-if="column.key === 'name'">
                            <div v-if="editingProcessKey === record.key">
                                <a-input v-model:value="record.name" @pressEnter="handleProcessSave(record.key)" />
                            </div>
                            <div v-else>
                                {{ record.name }}
                            </div>
                        </template>
                        <template v-if="column.key === 'description'">
                            <div v-if="editingProcessKey === record.key">
                                <a-input v-model:value="record.description"
                                    @pressEnter="handleProcessSave(record.key)" />
                            </div>
                            <div v-else>
                                {{ record.description }}
                            </div>
                        </template>
                        <template v-if="column.key === 'action'">
                            <div class="action-buttons">
                                <template v-if="editingProcessKey === record.key">
                                    <a @click="handleProcessSave(record.key)">保存</a>
                                </template>
                                <template v-else>
                                    <a @click="handleProcessEdit(record)">编辑</a>
                                </template>
                                <a-divider type="vertical" />
                                <a-popconfirm title="确定要删除这行吗?" @confirm="handleProcessDelete(record.key)">
                                    <a>删除</a>
                                </a-popconfirm>
                            </div>
                        </template>
                    </template>
                </a-table>

                <div style="margin-top: 16px">
                    <a-button type="primary" @click="handleProcessAdd">添加功能过程</a-button>
                </div>
            </div>

            <!-- 识别数据组 -->
            <div v-if="subStep === 3">
                <h2>识别数据组</h2>
                <p class="content">请识别系统中的数据组（数据组是在功能过程中移动的数据属性的唯一、非空、无序、无重复的集合）</p>

                <a-table :columns="dataGroupColumns" :dataSource="dataGroups" :pagination="false">
                    <template #bodyCell="{ column, record, index }">
                        <template v-if="column.key === 'index'">
                            {{ index + 1 }}
                        </template>
                        <template v-if="column.key === 'name'">
                            <div v-if="editingGroupKey === record.key">
                                <a-input v-model:value="record.name" @pressEnter="handleGroupSave(record.key)" />
                            </div>
                            <div v-else>
                                {{ record.name }}
                            </div>
                        </template>
                        <template v-if="column.key === 'description'">
                            <div v-if="editingGroupKey === record.key">
                                <a-input v-model:value="record.description" @pressEnter="handleGroupSave(record.key)" />
                            </div>
                            <div v-else>
                                {{ record.description }}
                            </div>
                        </template>
                        <template v-if="column.key === 'action'">
                            <div class="action-buttons">
                                <template v-if="editingGroupKey === record.key">
                                    <a @click="handleGroupSave(record.key)">保存</a>
                                </template>
                                <template v-else>
                                    <a @click="handleGroupEdit(record)">编辑</a>
                                </template>
                                <a-divider type="vertical" />
                                <a-popconfirm title="确定要删除这行吗?" @confirm="handleGroupDelete(record.key)">
                                    <a>删除</a>
                                </a-popconfirm>
                            </div>
                        </template>
                    </template>
                </a-table>

                <div style="margin-top: 16px">
                    <a-button type="primary" @click="handleGroupAdd">添加数据组</a-button>
                </div>
            </div>

            <!-- 数据组移动表格 -->
            <div v-if="subStep === 4">
                <h2>识别数据组移动</h2>
                <p class="content">请识别每个功能过程中的数据组移动情况</p>
                <div class="method-step">
                    <h4>计算功能规模</h4>
                    <p class="content">
                        每个数据移动计为1 CFP（COSMIC Function Point）。软件的总功能规模为所有数据移动的CFP之和。
                    </p>
                    <div class="formula-box">
                        <p>总功能规模 = Σ(输入 + 输出 + 读取 + 写入)</p>
                    </div>
                </div>
                <a-table :columns="dataMovementColumns" :dataSource="dataMovements" :pagination="false"
                    class="movement-table">
                    <template #bodyCell="{ column, record, index }">
                        <template v-if="column.key === 'process'">
                            <a-select v-model:value="record.process" style="width: 100%"
                                @change="handleProcessChange(record.key)">
                                <a-select-option v-for="process in functionalProcesses" :key="process.key"
                                    :value="process.name">
                                    {{ process.name }}
                                </a-select-option>
                            </a-select>
                        </template>

                        <template v-if="column.key === 'dataGroup'">
                            <a-select v-model:value="record.dataGroup" style="width: 100%">
                                <a-select-option v-for="group in dataGroups" :key="group.key" :value="group.name">
                                    {{ group.name }}
                                </a-select-option>
                            </a-select>
                        </template>

                        <template v-if="column.key === 'movementType'">
                            <a-select v-model:value="record.movementType" style="width: 100%">
                                <a-select-option value="输入">输入</a-select-option>
                                <a-select-option value="输出">输出</a-select-option>
                                <a-select-option value="读">读</a-select-option>
                                <a-select-option value="写">写</a-select-option>
                            </a-select>
                        </template>

                        <template v-if="column.key === 'action'">
                            <div class="action-buttons">
                                <a-button type="link" @click="addDataGroupToProcess(record)">添加数据组</a-button>
                                <a-divider type="vertical" />
                                <a-popconfirm title="确定要删除这行吗?" @confirm="handleMovementDelete(record.key)">
                                    <a>删除</a>
                                </a-popconfirm>
                            </div>
                        </template>
                    </template>
                </a-table>

                <div style="margin-top: 16px">
                    <a-button type="primary" @click="addNewProcess" style="margin-right: 16px">添加功能过程</a-button>
                    <a-button type="primary" @click="calculateResults">计算规模</a-button>
                </div>
            </div>

            <!-- 结果预览 -->
            <div v-if="subStep === 5">
                <h2>结果预览</h2>

                <!-- 功能用户预览 -->
                <a-table :columns="previewUserColumns" :dataSource="functionalUsers" :pagination="false"
                    style="margin-bottom: 24px">
                    <template #title>功能用户预览</template>
                </a-table>

                <!-- 功能过程预览 -->
                <a-table :columns="previewProcessColumns" :dataSource="functionalProcesses" :pagination="false"
                    style="margin-bottom: 24px">
                    <template #title>功能过程预览</template>
                </a-table>

                <!-- 数据组预览 -->
                <a-table :columns="previewGroupColumns" :dataSource="dataGroups" :pagination="false">
                    <template #title>数据组预览</template>
                </a-table>
            </div>

            <!-- 软件规模计算结果 -->
            <div v-if="subStep === 6">
                <h2>软件规模计算结果</h2>
                <p class="content">根据前面步骤分析结果，计算最终的软件规模。</p>

                <div class="result-summary">
                    <div class="movement-statistics">
                        <h3>数据移动统计</h3>
                        <a-table :columns="statisticsColumns" :dataSource="movementStatistics" :pagination="false"
                            size="small" />
                    </div>

                    <a-card title="规模计算结果汇总" :bordered="false" style="margin-top: 24px;">
                        <a-descriptions bordered>
                            <a-descriptions-item label="功能用户数量">
                                {{ functionalUsers.length }}
                            </a-descriptions-item>
                            <a-descriptions-item label="功能过程数量">
                                {{ functionalProcesses.length }}
                            </a-descriptions-item>
                            <a-descriptions-item label="数据组数量">
                                {{ dataGroups.length }}
                            </a-descriptions-item>
                            <a-descriptions-item label="总CFP">
                                {{ calculateTotalCFP() }}
                            </a-descriptions-item>
                        </a-descriptions>
                    </a-card>
                </div>
            </div>
        </div>

        <!-- 实验总结 -->
        <div class="exp-step-card" v-if="mainStep === 4">
            <h2>实验总结</h2>
            <p class="content">请总结本次实验的主要内容、心得体会和遇到的问题等。</p>
            <div class="summary-container">
                <a-form-item>
                    <a-textarea v-model:value="experimentSummary" :rows="10" placeholder="请在此输入实验总结..."
                        :auto-size="{ minRows: 10, maxRows: 20 }" />
                </a-form-item>
              
                <div class="summary-footer">
                    <span class="summary-tip">提示：实验总结将会被保存并写入最终的实验报告中</span>
                </div>
            </div>
        </div>
    </div>

    <!-- 导航按钮 -->
    <div class="steps-action">
        <a-button v-if="mainStep === 3 && subStep === 5" type="danger" @click="showClearDataConfirm" style="margin-right: 8px">
            清除实验数据
        </a-button>

        <a-button v-if="mainStep === 3" style="margin-left: 8px; float: left;" @click="prevSub" :disabled="subStep === 0">
            <step-backward-outlined />上一步
        </a-button>
        <a-button v-if="mainStep === 3" type="primary" @click="nextSub" :disabled="subStep === subSteps.length - 1" style="margin-right: 8px;">
            <step-forward-outlined />下一步
        </a-button>

        <a-button v-if="mainStep !== 3" style="margin-left: 8px; float: left;" @click="prevMain" :disabled="mainStep === 0">
            <step-backward-outlined />上一步
        </a-button>
        <a-button v-if="mainStep !== 3 && mainStep !== mainSteps.length - 1" type="primary" @click="nextMain" style="margin-right: 8px;">
            <step-forward-outlined />下一步
        </a-button>
        <!-- 在最后一步提示提交按钮 -->
        <a-button v-if="mainStep === mainSteps.length - 1" type="primary" @click="handleSubmit" style="margin-right: 7px">
            提交
        </a-button>
        <a-button v-if="mainStep === mainSteps.length - 1" type="primary" class="btn" @click="downLoadFile">
            <EyeOutlined />
            预览报告
        </a-button>
    </div>

    <!-- 添加提示弹窗 -->
    <a-modal v-model:visible="validationModal.visible" title="提示" :closable="false" :maskClosable="false"
        @ok="handleModalOk">
        <p>{{ validationModal.message }}</p>
    </a-modal>

    <!-- 添加确认弹窗 -->
    <a-modal v-model:visible="clearDataModal.visible" title="确认清除数据" @ok="confirmClearData" @cancel="cancelClearData"
        :okText="'确认清除'" :cancelText="'取消'">
        <p style="color: #ff4d4f;">警告：此操作将清除所有实验数据且无法恢复，是否确认继续？</p>
    </a-modal>
</template>

<script lang="ts">
import { message } from 'ant-design-vue';
import { computed, onMounted, reactive, ref, getCurrentInstance } from 'vue';
import { useExperimentStore } from '@/store/experiment';
import { useAccountStore } from '@/store/account';
const experimentStore = useExperimentStore();
const accountStore = useAccountStore();

const receivedData = ref(null);
const experimentDate = new Date();

// 页面加载时安排方法调用
let cosmicComponent; // 保存组件实例的引用

onMounted(() => {
    // 获取组件实例，在mounted钩子中this指向当前组件实例
    cosmicComponent = getCurrentInstance()?.proxy;

    if (cosmicComponent) {
        cosmicComponent.loadFunctionalUsers();
        cosmicComponent.loadFunctionalProcesses();
        cosmicComponent.loadDataGroups();
        cosmicComponent.loadDataMovements();
        cosmicComponent.loadSummary();

        // 恢复页面状态
        const savedMainStep = sessionStorage.getItem('mainStep');
        const savedSubStep = sessionStorage.getItem('subStep');
        if (savedMainStep !== null) cosmicComponent.mainStep = Number(savedMainStep);
        if (savedSubStep !== null) cosmicComponent.subStep = Number(savedSubStep);
    }

    // 尝试从会话存储恢复报告数据
    const savedReportData = sessionStorage.getItem('cosmicReportData');
    if (savedReportData) {
        receivedData.value = JSON.parse(savedReportData);
    }

    const savedData = localStorage.getItem('experimentData');
    if (savedData) {
        receivedData.value = JSON.parse(savedData);
    }
});

export default {
    setup(props, { emit }) {
        // 将原来在 data() 中的状态转换为响应式数据
        const mainStep = ref(0);
        const subStep = ref(0);
        const mainSteps = ref([
            { title: '实验目的' },
            { title: '实验原理' },
            { title: '实验方法' },
            { title: '实验步骤' },
            { title: '实验总结' }
        ]);
        const subSteps = ref([
            { title: '确定测量目标' },
            { title: '识别功能用户' },
            { title: '识别功能过程' },
            { title: '识别数据组' },
            { title: '识别数据组移动' },
            { title: '结果预览' },
            { title: '软件规模计算结果' }
        ]);

        const tableData = ref([]);
        const formState1 = reactive({
            strategy: {
                measurementPurpose: '',
                softwareBlockSize: '',
                softwareBlockDecompositionLevel: '',
                softwareBlockFunctionalUser: '',
                softwareArchitectureLevel: '',
            },
        });
        const formState2 = reactive({
            input: {
                functionprocessing: '',
                dataItem: '',
                input: 0,
                output: 0,
                write: 0,
                read: 0,
                LocalFP: '',
                experience: '',
            },
        });

        // 计算属性
        const sumFP = computed(() => {
            const { input, output, write, read, LocalFP } = formState2.input;
            const regex = /\d+/g;
            const numbers = LocalFP.match(regex);
            const sum = numbers ? numbers.reduce((acc, cur) => acc + Number(cur), 0) : 0;
            return Number(input) + Number(output) + Number(write) + Number(read) + sum;
        });

        const handleStepChange = (current: number) => {
            // 阻止步骤的改变
            return false;
        };



        return {
            mainStep,
            subStep,
            mainSteps,
            subSteps,
            tableData,
            formState1,
            formState2,
            sumFP,
            handleStepChange,
        };
    },
    data() {
        return {
            functionalProcessesLength: null,
            sum: '',
            visible: false,
            current: 0,
            message,
            layout: {
                labelCol: { span: 4 },
                wrapperCol: { span: 16 },
            },
            validateMessages: {
                required: '${label} 必填',
                types: {
                    email: '${label} 格式不正确',
                    number: '${label} 格式不正确',
                },
                number: {
                    range: '${label} 必须在 ${min} 和 ${max} 之间',
                },
            },
            idCounter: 0,
            rowSelection: {
                onChange: (selectedRowKeys) => {
                    console.log('Selected Row Keys:', selectedRowKeys);
                },
            },
            columns: [
                { title: '功能处理', dataIndex: 'process', key: 'process' },
                { title: '输入', dataIndex: 'input', key: 'input', slots: { customRender: 'inputs' } },
                { title: '输出', dataIndex: 'output', key: 'output', slots: { customRender: 'outputs' } },
                { title: '读', dataIndex: 'read', key: 'read', slots: { customRender: 'reads' } },
                { title: '写', dataIndex: 'write', key: 'write', slots: { customRender: 'writes' } },
                { title: '统计', dataIndex: 'total', key: 'total', slots: { customRender: 'statistics' } }
            ],
            functionalUsers: ref([]),
            editingKey: '',
            functionalUserColumns: [
                {
                    title: '号',
                    key: 'index',
                    width: '15%'
                },
                {
                    title: '功能用户',
                    key: 'name',
                    width: '60%'
                },
                {
                    title: '操作',
                    key: 'action',
                    width: '25%'
                }
            ],
            functionalProcessColumns: [
                {
                    title: '序号',
                    key: 'index',
                    width: '10%'
                },
                {
                    title: '功能过程',
                    key: 'name',
                    width: '30%'
                },
                {
                    title: '功能描述',
                    key: 'description',
                    width: '40%'
                },
                {
                    title: '操作',
                    key: 'action',
                    width: '20%'
                }
            ],
            functionalProcesses: ref([]),
            editingProcessKey: '',
            dataGroups: ref([]),
            editingGroupKey: '',
            dataGroupColumns: [
                {
                    title: '编号',
                    key: 'index',
                    width: '10%'
                },
                {
                    title: '数据组名称',
                    key: 'name',
                    width: '30%'
                },
                {
                    title: '数据组描述',
                    key: 'description',
                    width: '40%'
                },
                {
                    title: '操作',
                    key: 'action',
                    width: '20%'
                }
            ],
            dataMovements: ref([]),
            dataMovementColumns: [
                {
                    title: '功能过程',
                    key: 'process',
                    width: '30%'
                },
                {
                    title: '数据组',
                    key: 'dataGroup',
                    width: '30%'
                },
                {
                    title: '数据移动类型',
                    key: 'movementType',
                    width: '20%'
                },
                {
                    title: '操作',
                    key: 'action',
                    width: '20%'
                }
            ],
            statisticsColumns: [
                {
                    title: '功能过程',
                    dataIndex: 'process',
                    key: 'process',
                },
                {
                    title: '输入',
                    dataIndex: 'entry',
                    key: 'entry',
                },
                {
                    title: '输出',
                    dataIndex: 'exit',
                    key: 'exit',
                },
                {
                    title: '读',
                    dataIndex: 'read',
                    key: 'read',
                },
                {
                    title: '写',
                    dataIndex: 'write',
                    key: 'write',
                },
                {
                    title: '总计',
                    dataIndex: 'total',
                    key: 'total',
                }
            ],
            movementStatistics: [],
            validationModal: {
                visible: false,
                message: ''
            },
            experimentSummary: ref(''),
            clearDataModal: {
                visible: false
            },
            // 预览表格的列定义
            previewUserColumns: [
                {
                    title: '序号',
                    key: 'index',
                    customRender: ({ index }) => index + 1,
                    width: '80px'
                },
                {
                    title: '功能用户名称',
                    dataIndex: 'name',
                    key: 'name'
                }
            ],
            previewProcessColumns: [
                {
                    title: '序号',
                    key: 'index',
                    customRender: ({ index }) => index + 1,
                    width: '80px'
                },
                {
                    title: '功能过程名称',
                    dataIndex: 'name',
                    key: 'name'
                },
                {
                    title: '功能描述',
                    dataIndex: 'description',
                    key: 'description'
                }
            ],
            previewGroupColumns: [
                {
                    title: '序号',
                    key: 'index',
                    customRender: ({ index }) => index + 1,
                    width: '80px'
                },
                {
                    title: '数据组名称',
                    dataIndex: 'name',
                    key: 'name'
                },
                {
                    title: '数据组描述',
                    dataIndex: 'description',
                    key: 'description'
                }
            ],
        }
    },
    methods: {
        nextMain() {
            let canProceed = true;

            // 根据当前步骤执行相应的验证
            if (this.mainStep === 4) {
                // 实验总结页面的验证
                if (!this.experimentSummary.trim()) {
                    this.showValidationModal('请填写实验总结后继续');
                    return;
                }
            } else if (this.mainStep === 3) {
                // 实验步骤页面的验证
                switch (this.subStep) {
                    case 1:
                        canProceed = this.validateFunctionalUsers();
                        break;
                    case 2:
                        canProceed = this.validateFunctionalProcesses();
                        break;
                    case 3:
                        canProceed = this.validateDataGroups();
                        break;
                    case 4:
                        canProceed = this.validateDataMovements();
                        break;
                }
            }

            if (!canProceed) {
                return;
            }

            // 通过验证后执行页面切换
            if (this.mainStep < this.mainSteps.length - 1) {
                this.mainStep++;
            }
        },
        prevMain() {
            if (this.mainStep > 0) {
                this.mainStep--;
            }
        },
        nextSub() {
            // 根据当前步骤进行相应的验证
            let canProceed = true;
            switch (this.subStep) {
                case 1: // 功能用户页面
                    canProceed = this.validateFunctionalUsers();
                    break;
                case 2: // 功能过程页面
                    canProceed = this.validateFunctionalProcesses();
                    break;
                case 3: // 数据组页面
                    canProceed = this.validateDataGroups();
                    break;
                case 4: // 数据组移动页面
                    canProceed = this.validateDataMovements();
                    break;
            }

            if (!canProceed) {
                return;
            }

            // 验证通过，继续下一步
            if (this.subStep < this.subSteps.length - 1) {
                this.subStep++;
            }
        },
        prevSub() {
            if (this.subStep > 0) {
                this.subStep--;
            }
        },

        handleAdd() {
            const newKey = `user-${Date.now()}`;
            this.functionalUsers.push({
                key: newKey,
                name: '新功能用户',
            });
            this.saveFunctionalUsers();
        },

        handleEdit(record) {
            this.editingKey = record.key;
        },

        handleSave(key) {
            this.editingKey = '';
            this.saveFunctionalUsers();
        },

        handleDelete(key) {
            this.functionalUsers = this.functionalUsers.filter(item => item.key !== key);
            this.saveFunctionalUsers();
        },

        saveFunctionalUsers() {
            localStorage.setItem('functionalUsers', JSON.stringify(this.functionalUsers));
        },

        loadFunctionalUsers() {
            const savedUsers = localStorage.getItem('functionalUsers');
            if (savedUsers) {
                this.functionalUsers = JSON.parse(savedUsers);
            }
        },

        handleProcessAdd() {
            const newKey = `process-${Date.now()}`;
            this.functionalProcesses.push({
                key: newKey,
                name: '新功能过程',
                description: '请添加功能描述'
            });
            this.saveFunctionalProcesses();
        },

        handleProcessEdit(record) {
            this.editingProcessKey = record.key;
        },

        handleProcessSave(key) {
            this.editingProcessKey = '';
            this.saveFunctionalProcesses();
        },

        handleProcessDelete(key) {
            this.functionalProcesses = this.functionalProcesses.filter(item => item.key !== key);
            this.saveFunctionalProcesses();
        },

        saveFunctionalProcesses() {
            localStorage.setItem('functionalProcesses', JSON.stringify(this.functionalProcesses));
        },

        loadFunctionalProcesses() {
            const savedProcesses = localStorage.getItem('functionalProcesses');
            if (savedProcesses) {
                this.functionalProcesses = JSON.parse(savedProcesses);
            }
        },

        handleGroupAdd() {
            const newKey = `group-${Date.now()}`;
            this.dataGroups.push({
                key: newKey,
                name: '新数据组',
                description: '请添加数据组描述'
            });
            this.saveDataGroups();
        },

        handleGroupEdit(record) {
            this.editingGroupKey = record.key;
        },

        handleGroupSave(key) {
            this.editingGroupKey = '';
            this.saveDataGroups();
        },

        handleGroupDelete(key) {
            this.dataGroups = this.dataGroups.filter(item => item.key !== key);
            this.saveDataGroups();
        },

        saveDataGroups() {
            localStorage.setItem('dataGroups', JSON.stringify(this.dataGroups));
        },

        loadDataGroups() {
            const savedGroups = localStorage.getItem('dataGroups');
            if (savedGroups) {
                this.dataGroups = JSON.parse(savedGroups);
            }
        },

        addDataGroupToProcess(record) {
            const newKey = `movement-${Date.now()}`;
            this.dataMovements.push({
                key: newKey,
                process: record.process,
                dataGroup: '',
                movementType: '',
                parentKey: record.parentKey || record.key
            });
            this.saveDataMovements();
        },

        handleProcessChange(key) {
            const changedRecord = this.dataMovements.find(item => item.key === key);
            if (changedRecord) {
                this.dataMovements.forEach(item => {
                    if (item.parentKey === changedRecord.parentKey) {
                        item.process = changedRecord.process;
                    }
                });
            }
            this.saveDataMovements();
            this.updateStatistics();
        },

        handleMovementDelete(key) {
            this.dataMovements = this.dataMovements.filter(item =>
                item.key !== key && item.parentKey !== key
            );
            this.saveDataMovements();
            this.updateStatistics();
        },

        saveDataMovements() {
            localStorage.setItem('dataMovements', JSON.stringify(this.dataMovements));
            this.updateStatistics();
        },

        loadDataMovements() {
            const savedMovements = localStorage.getItem('dataMovements');
            if (savedMovements) {
                this.dataMovements = JSON.parse(savedMovements);
                this.updateStatistics();
            }
        },

        addNewProcess() {
            const newKey = `movement-${Date.now()}`;
            this.dataMovements.push({
                key: newKey,
                process: '',
                dataGroup: '',
                movementType: '',
                parentKey: newKey
            });
            this.saveDataMovements();
        },

        updateStatistics() {
            const stats = {};

            // 清空之前的统计数据
            this.movementStatistics = [];

            this.dataMovements.forEach(movement => {
                if (!movement.process || !movement.movementType) return;

                if (!stats[movement.process]) {
                    stats[movement.process] = {
                        process: movement.process,
                        entry: 0,
                        exit: 0,
                        read: 0,
                        write: 0,
                        total: 0
                    };
                }

                switch (movement.movementType) {
                    case '输入': stats[movement.process].entry++; break;
                    case '输出': stats[movement.process].exit++; break;
                    case '读': stats[movement.process].read++; break;
                    case '写': stats[movement.process].write++; break;
                }

                stats[movement.process].total =
                    stats[movement.process].entry +
                    stats[movement.process].exit +
                    stats[movement.process].read +
                    stats[movement.process].write;
            });

            this.movementStatistics = Object.values(stats);

            // 保存统计结果到本地存储
            localStorage.setItem('movementStatistics', JSON.stringify(this.movementStatistics));
        },

        // 显示验证弹窗
        showValidationModal(message) {
            this.validationModal.message = message;
            this.validationModal.visible = true;
        },

        // 处理弹窗确认
        handleModalOk() {
            this.validationModal.visible = false;
        },

        // 修改验证功能用户的方法
        validateFunctionalUsers() {
            if (this.functionalUsers.length === 0) {
                this.showValidationModal('请至少添加一个功能用户');
                return false;
            }

            const emptyUsers = this.functionalUsers.filter(user => !user.name.trim());
            if (emptyUsers.length > 0) {
                this.showValidationModal('存在未填写名称的功能用户，请完善后继续');
                return false;
            }

            // 检查重复名称
            const names = this.functionalUsers.map(user => user.name.trim());
            const duplicates = names.filter((name, index) => names.indexOf(name) !== index);
            if (duplicates.length > 0) {
                this.showValidationModal(`发现重复的功能用户名称：${duplicates.join(', ')}，请修改后继续`);
                return false;
            }

            return true;
        },

        // 修改验证功能过程的方法
        validateFunctionalProcesses() {
            if (this.functionalProcesses.length === 0) {
                this.showValidationModal('请至少添加一个功能过程');
                return false;
            }

            const emptyProcesses = this.functionalProcesses.filter(
                process => !process.name.trim() || !process.description.trim()
            );
            if (emptyProcesses.length > 0) {
                this.showValidationModal('存在未完善的功能过程信息，请填写名称和描述后继续');
                return false;
            }

            // 检查重复名称
            const names = this.functionalProcesses.map(process => process.name.trim());
            const duplicates = names.filter((name, index) => names.indexOf(name) !== index);
            if (duplicates.length > 0) {
                this.showValidationModal(`发现重复的功能过程名称：${duplicates.join(', ')}，请修改后继续`);
                return false;
            }

            return true;
        },

        // 修改验证数据组的方法
        validateDataGroups() {
            if (this.dataGroups.length === 0) {
                this.showValidationModal('请至少添加一个数据组');
                return false;
            }

            const emptyGroups = this.dataGroups.filter(
                group => !group.name.trim() || !group.description.trim()
            );
            if (emptyGroups.length > 0) {
                this.showValidationModal('存在未完善的数据组信息，请填写名称和描述后继续');
                return false;
            }

            // 检查重复名称
            const names = this.dataGroups.map(group => group.name.trim());
            const duplicates = names.filter((name, index) => names.indexOf(name) !== index);
            if (duplicates.length > 0) {
                this.showValidationModal(`发现重复的数据组名称：${duplicates.join(', ')}，请修改后继续`);
                return false;
            }

            return true;
        },

        validateDataMovements() {
            if (this.dataMovements.length === 0) {
                this.showValidationModal('请至少添加一个数据移动记录');
                return false;
            }

            const incompleteItems = this.dataMovements.filter(movement => {
                return !movement.process || !movement.dataGroup || !movement.movementType;
            });

            if (incompleteItems.length > 0) {
                const incompleteFields = incompleteItems.map((item, index) => {
                    const missing = [];
                    if (!item.process) missing.push('功能过程');
                    if (!item.dataGroup) missing.push('数据组');
                    if (!item.movementType) missing.push('数据移动类型');
                    return `第 ${index + 1} 行缺少: ${missing.join('、')}`;
                });

                this.showValidationModal(`请完善以下数据移动信息：\n${incompleteFields.join('\n')}`);
                return false;
            }
            return true;
        },

        downLoadFile() {
            if (!receivedData.value) {
                // 如果还没有提交过，则自动生成报告数据
                // this.handleSubmit(false); // 传入false表示不显示提交成功消息
                message.error('报告数据未准备好，请先填写完整的实验数据并提交。');
                return;
            }

            // 使用从子组件接收的数据来填充 reportData
            const reportData = {
                course_id: accountStore.course.courseId,
                experiment_id: 15,
                reporter_id: accountStore.account.email.slice(0, accountStore.account.email.indexOf('@')),
                reporter_name: accountStore.account.name,
                course_name: accountStore.course.courseName,
                submit_time: new Date().getTime(),
                // ... 其他需要发送到服务器的报告数据
                // 使用 receivedData.value 中的数据
                ...receivedData.value,

            };
            console.log('functionalUsers 长度:', this.functionalProcesses.length);
            console.log('预览报告数据:', reportData); // 调试输出

            try {
                // 将数据转为URL参数格式
                const reportDataStr = encodeURIComponent(JSON.stringify(reportData));

                // 跳转到报告预览页面
                this.$router.push({
                    path: '/report-preview',
                    query: {
                        reportData: reportDataStr
                    }
                });
            } catch (err) {
                console.error('路由跳转错误:', err);
                message.error('预览报告时出错: ' + err.message);
            }
        },

        // 实现状态保存和恢复
        created() {
            this.loadFunctionalUsers();
            this.loadFunctionalProcesses();
            this.loadDataGroups();
            this.loadDataMovements();
            this.loadSummary();
            // 恢复页面状态
            const savedMainStep = sessionStorage.getItem('mainStep');
            const savedSubStep = sessionStorage.getItem('subStep');
            if (savedMainStep !== null) this.mainStep = Number(savedMainStep);
            if (savedSubStep !== null) this.subStep = Number(savedSubStep);

            // 尝试从会话存储恢复报告数据
            const savedReportData = sessionStorage.getItem('cosmicReportData');
            if (savedReportData) {
                receivedData.value = JSON.parse(savedReportData);
            }
        },

        beforeRouteLeave(to, from, next) {
            // 保存页面状态
            sessionStorage.setItem('mainStep', this.mainStep.toString());
            sessionStorage.setItem('subStep', this.subStep.toString());
            next();
        },

        calculateTotalCFP() {
            return this.movementStatistics.reduce((total, item) => {
                return total + (item.total || 0);
            }, 0);
        },

        handleStepChange(current) {
            // 阻止步骤的改变
            return false;
        },

        handleSummaryChange() {
            this.saveSummary();
        },

        saveSummary() {
            localStorage.setItem('experimentSummary', this.experimentSummary);
            this.$message.success('实验总结已保存');
        },

        loadSummary() {
            const savedSummary = localStorage.getItem('experimentSummary');
            if (savedSummary) {
                this.experimentSummary = savedSummary;
            }
        },

        validateSummary() {
            if (!this.experimentSummary.trim()) {
                this.showValidationModal('请填写实验总结后继续');
                return false;
            }
            return true;
        },

        handleSubmit(showMessage = true) {
            if (!this.validateAllData()) {
                return;
            }

            const data = {
                // 基础数据
                formState1: this.formState1,
                formState2: this.formState2,

                // 实验过程数据
                functionalUsers: this.functionalUsers,
                functionalUsersLength: this.functionalUsers.length,

                functionalProcesses: this.functionalProcesses,
                functionalProcessesLength: this.functionalProcesses.length,

                dataGroups: this.dataGroups,
                dataGroupsLength: this.dataGroups.length,

                // 数据移动数据
                dataMovements: this.dataMovements,
                dataMovementsLength: this.dataMovements.length,

                // 移动统计数据
                movementStatistics: this.movementStatistics,

                // 计算结果
                totalCFP: this.calculateTotalCFP(),

                // 实验总结
                experimentSummary: this.experimentSummary,
            };

            receivedData.value = data;
            localStorage.setItem('experimentData', JSON.stringify(data));

            // 将报告数据保存到会话存储中
            sessionStorage.setItem('cosmicReportData', JSON.stringify(data));

            this.$emit('submit', data);
            if (showMessage) {
                this.$message.success('实验报告提交成功！');
            }
        },

        validateAllData() {
            // 验证功能用户
            if (!this.validateFunctionalUsers()) {
                this.showValidationModal('请完善功能用户信息');
                return false;
            }

            // 验证功能过程
            if (!this.validateFunctionalProcesses()) {
                this.showValidationModal('请完善功能过程信息');
                return false;
            }

            // 验证数据组
            if (!this.validateDataGroups()) {
                this.showValidationModal('请完善数据组信息');
                return false;
            }

            // 验证数据移动
            if (!this.validateDataMovements()) {
                this.showValidationModal('请完善数据移动信息');
                return false;
            }

            // 验证实验总结
            if (!this.validateSummary()) {
                this.showValidationModal('请完成实验总结');
                return false;
            }

            return true;
        },

        // 保存所有数据到本地存储
        saveAllData() {
            this.saveFunctionalUsers();
            this.saveFunctionalProcesses();
            this.saveDataGroups();
            this.saveDataMovements();
            this.saveSummary();
        },

        // 添加新方法
        calculateResults() {
            // 验证数据完整性
            if (!this.validateDataMovements()) {
                return;
            }

            // 更新统计数据
            this.updateStatistics();

            // 显示成功消息
            this.$message.success('规模计算完成');

            // 自动跳转到结果页面
            this.subStep = 5;
        },

        // 显示清除数据确认弹窗
        showClearDataConfirm() {
            this.clearDataModal.visible = true;
        },

        // 确认清除数据
        confirmClearData() {
            // 清除本地存储中的实验数据
            localStorage.removeItem('functionalUsers');
            localStorage.removeItem('functionalProcesses');
            localStorage.removeItem('dataGroups');
            localStorage.removeItem('dataMovements');
            localStorage.removeItem('experimentSummary');
            message.success('实验数据已清除');

            // 清空组件中的数据
            this.functionalUsers = [];
            this.functionalProcesses = [];
            this.dataGroups = [];
            this.dataMovements = [];
            this.experimentSummary = '';

            // 关闭弹窗并显示成功消息
            this.clearDataModal.visible = false;
            this.$message.success('实验数据已清除');
        },

        // 取消清除数据
        cancelClearData() {
            this.clearDataModal.visible = false;
        },
    },
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
    text-align: left;
    padding: 16px;
}

/* 如果内容为空，隐藏整个容器 */
.steps-content:empty {
    display: none;
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
    text-align: right;
}

[data-theme='dark'] .steps-content {
    background-color: #2f2f2f;
    border: 1px dashed #404040;
}

.exp-step-card {
    padding-inline: 14px;
}

.action-buttons {
    display: flex;
    gap: 8px;
}

.action-buttons a {
    color: #1890ff;
    cursor: pointer;
}

.action-buttons a:hover {
    color: #40a9ff;
}

.sub-steps {
    margin-top: 16px;
    margin-bottom: 16px;
    padding: 8px 0;
}

/* 可选：调整子步骤的标题样式 */
:deep(.ant-steps-item-title) {
    font-size: 14px;
}

.result-summary {
    margin-top: 20px;
}

.movement-statistics {
    padding: 16px;
    background: #fafafa;
    border: 1px dashed #e9e9e9;
    border-radius: 6px;
}

.movement-statistics h3 {
    margin-bottom: 16px;
    font-weight: 500;
}

:deep(.ant-descriptions-item-label) {
    width: 120px;
    font-weight: 500;
}

:deep(.ant-card) {
    box-shadow: 0 1px 2px rgba(0, 0, 0, 0.03);
}

/* 添加弹窗内容样式 */
:deep(.ant-modal-body) {
    white-space: pre-line;
    /* 保留换行符 */
    max-height: 400px;
    overflow-y: auto;
}

:deep(.ant-modal-footer) {
    text-align: center;
}

.summary-container {
    margin: 20px;
    padding: 20px;
    background: #fff;
    border-radius: 8px;
    box-shadow: 0 1px 2px rgba(0, 0, 0, 0.03);
}

.summary-footer {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 16px;
}

.summary-tip {
    color: #999;
    font-size: 14px;
}

:deep(.ant-input) {
    font-size: 14px;
    line-height: 1.6;
}

:deep(.ant-input:focus) {
    box-shadow: 0 0 0 2px rgba(24, 144, 255, 0.2);
}

.principle-content {
    padding: 20px;
}

.principle-content h3 {
    color: #1890ff;
    margin: 24px 0 16px 0;
    font-weight: 500;
}

.principle-content h4 {
    color: #333;
    margin: 16px 0 8px 0;
    font-weight: 500;
}

.content {
    text-indent: 2em;
    line-height: 1.8;
    color: #333;
    margin-bottom: 16px;
}

.principle-list {
    margin-left: 40px;
    margin-bottom: 20px;
}

.principle-list li {
    line-height: 1.8;
    color: #666;
    margin-bottom: 8px;
}

.movement-types {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
    gap: 20px;
    margin: 20px 0;
}

.movement-type {
    background: #f5f5f5;
    padding: 16px;
    border-radius: 8px;
    border-left: 4px solid #1890ff;
}

.movement-type h4 {
    color: #1890ff;
    margin-bottom: 8px;
}

.movement-type p {
    color: #666;
    margin: 0;
}

.principle-note {
    background: #f6ffed;
    border: 1px solid #b7eb8f;
    border-radius: 8px;
    padding: 16px;
    margin-top: 24px;
}

.principle-note h4 {
    color: #52c41a;
    margin-top: 0;
}

.principle-note ul {
    margin-bottom: 0;
}

.principle-note li {
    color: #666;
    line-height: 1.6;
    margin-bottom: 4px;
}

/* 预览表格样式 */
:deep(.ant-table-title) {
    font-weight: 500;
    font-size: 16px;
    padding: 16px 24px;
    background: #fafafa;
}

:deep(.ant-table) {
    margin-bottom: 24px;
    border-radius: 8px;
    box-shadow: 0 1px 2px rgba(0, 0, 0, 0.03);
}

:deep(.ant-table-thead > tr > th) {
    background: #f5f5f5;
}
</style>