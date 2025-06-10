<template>
    <div>
        <a-steps v-model:current="current" size="small">
            <a-step v-for="item in steps" :key="item.title" :title="item.title" />
        </a-steps>
        <div class="steps-content">
            <div class="exp-step-card" v-if="current == 0" ref="content1">
                <h2>实验原理 </h2>
                <p>
                    段落
                </p>
            </div>
            <div class="exp-step-card" v-if="current == 1" ref="content2">
                <h2>
                    步骤1
                </h2>
                <p>
                    段落1
                </p>
            </div>
            <div class="exp-step-card" v-if="current == 2" ref="content3">
                <h2>
                    步骤2
                </h2>
                <p>
                    段落2
                </p>
            </div>
            <div class="exp-step-card" v-if="current == 3" ref="content4">
                <h2>
                    步骤3
                </h2>
                <p>
                    段落3
                </p>
            </div>
            <div class="exp-step-card" v-if="current == 4" ref="content5">
                <h2>
                    步骤4
                </h2>
                <p>
                    段落4
                </p>
            </div>
            <div class="exp-step-card" v-if="current == 5" ref="content6">
                <h2>
                    步骤5
                </h2>
                <p>
                    段落5
                </p>
            </div>
            <div class="exp-step-card" v-if="current == 6" ref="content7">
                <h2>
                    步骤6
                </h2>
                <p>
                    段落6
                </p>
            </div>
            <div class="exp-step-card" v-if="current == 7" ref="content8">
                <h2>
                    思考题
                </h2>
                <p>
                    段落
                </p>
            </div>

            <div class="exp-step-card" v-if="current == 8" ref="content9">
                <h2>
                    实验心得
                </h2>
                <a-form :model="summary" v-bind="layout" name="nest-messages">
                    <a-form-item label="请输入实验总结" name="summary">
                        <a-textarea v-model:value="summary" />
                    </a-form-item>
                </a-form>
            </div>
        </div>
        <div class="steps-action">
            <a-button v-if="current < steps.length - 1" type="primary" style="float: right;"
                @click="next"><step-forward-outlined />下一步</a-button>
            <a-button v-if="current > 0" style="margin-left: 8px" @click="prev"><step-backward-outlined />上一步</a-button>
        </div>

    </div>
    <div v-if="current == 8" style="margin-top: 20px; display: flex;justify-content: flex-end;">
        <a-spin :spinning="spinning">
            <div style="display: flex; justify-content: flex-end;margin-top: 20px;">
                <a-button type="primary" style="margin-right: 10px;" @click="submit">提交</a-button>
            </div>
        </a-spin>
    </div>
</template>


<script setup lang="ts" >
import { ref } from 'vue';
import { useStore } from './store'
import html2canvas from 'html2canvas';
import { useAccountStore } from '@/store/account';
import { useExperimentStore } from '@/store/experiment';
import { message } from 'ant-design-vue';
// 这里和持久化存储有关
const store = useStore()

// 加载动画
const spinning = ref<boolean>(false)
const content1 = ref('')
const content2 = ref('')
const content3 = ref('')
const content4 = ref('')
const content5 = ref('')
const content6 = ref('')
const content7 = ref('')
const content8 = ref('')
const content9 = ref('')

// 当前实验步骤的游标
const current = ref(0)

const layout = {
    labelCol: {
        span: 4,
    },
    wrapperCol: {
        span: 16,
    },
}
const summary = ref('')
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
{
    title: '步骤8',
    content: '8-content',
}
]

// 这里定义父级的提交函数
// 数据是：实验页面的数据收集到data，点击按钮的时候触发这里的submit函数，传参，再调用父级的submit
// 父级结合控制信息和数据信息，调用接口，提交数据
// **不需要修改**
const emit = defineEmits({
    submit: (data: any) => {
        return true
    }
})

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
    if (current.value == 7) {
        content.value.push(content8);
    }
    if (current.value == 8) {
        content.value.push(content9);
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
    if (current.value == 7) {
        content.value.push(content8);
    }
    current.value--;
}

// 保存实验数据
const clearData = () => {
    // 这里将页面数据保存到store中，关闭浏览器之后也可以持久化保存
    store.clear()
    message.success("清空成功");
}

// 需要图片上传的逻辑
const submit = () => {
    spinning.value = true;
    const { account } = useAccountStore()
    const { uploadImg } = useExperimentStore()
    const experiment_id = "01"
    let img1Url = ""
    // 此处根据你的html元素id进行更改
    html2canvas(document.getElementById("char")).then(function (canvas) {
        canvas.toBlob(
            function (blob) {
                //实验id，这里需要根据当前更改                           // 渲染到模板的图片名称，和下面image一一对应
                img1Url = uploadImg(blob, experiment_id, account.email.slice(0, account.email.indexOf("@")), "img1");
                console.log(img1Url);
                const data = {
                    // 此处为图片的链接，统一用image包裹，里面是图片名称：图片url
                    // 这里的图片名称就是最终渲染到docx模板上的名称，需要统一
                    image: {
                        img1: img1Url
                    },
                    // 此处为实验数据

                }
                // 数据提取完毕以后调用父组件的submit方法，并传递数据
                emit('submit', data)
                // 关闭加载动画
                spinning.value = false
            });
    })
}

// 不需要上传图表的逻辑
const submit1 = () => {
    spinning.value = true;
     const data = {
        // 此处为实验数据

    }
    // 数据提取完毕以后调用父组件的submit方法，并传递数据
    emit('submit', data)
    // 关闭加载动画
    spinning.value = false
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