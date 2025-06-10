import Mock from 'mockjs';

Mock.mock('api/menu/student_experiment', 'get', ({ body }) => {
    return {
        "success": true,
        "code": 0,
        "msg": "success",
        "data": [
            // 1. 供给与需求-实验
            {
                "id": "11",
                "title": "供给与需求-实验",
                "name": "tanpaifang61",
                "kind": "软件产品的供给与需求",
                "content": null,
                "file": null
            },
            // 2. 软件规模度量
            {
                "id": "21",
                "title": "IFPUG-方法",
                "name": "ifpug",
                "kind": "软件规模度量",
                "content": null,
                "file": "https://virtual-experiment-system.oss-cn-shanghai.aliyuncs.com/09-05-2023/969fb6b73d9a4c6abf253cf3e9eeeab7/软件工程管理与经济-实验指导书-实验二_小型软件项目规模度量实验.pdf"
            },
            {
                "id": "22",
                "title": "NESMA-方法",
                "name": "nesma",
                "kind": "软件规模度量",
                "content": null,
                "file": null
            },
            {
                "id": "28",
                "title": "NESMA-方法",
                "name": "nesma",
                "kind": "软件规模度量",
                "content": null,
                "file": null
            },
            {
                "id": "29",
                "title": "NESMA-方法",
                "name": "nesma",
                "kind": "软件规模度量",
                "content": null,
                "file": null
            },
            {
                "id": "20",
                "title": "NESMA-方法",
                "name": "nesma",
                "kind": "软件规模度量",
                "content": null,
                "file": null
            },
           
            {
                "id": "24",
                "title": "COSMIC-方法",
                "name": "cosmic",
                "kind": "软件规模度量",
                "content": null,
                "file": null
            },
            {
                "id": "25",
                "title": "国标 GB/T 36964-2018",
                "name": "gb11",
                "kind": "软件规模度量",
                "content": null,
                "file": null
            },
            {
                "id": "26",
                "title": "国标 GB/T 36964-2018",
                "name": "gb11",
                "kind": "软件规模度量",
                "content": null,
                "file": null
            },
            {
                "id": "27",
                "title": "国标 GB/T 36964-2018",
                "name": "gb11",
                "kind": "软件规模度量",
                "content": null,
                "file": null
            },

            {
                "id": "23",
                "title": "MK II-方法-建设中",
                "name": "markii",
                "kind": "软件规模度量",
                "content": null,
                "file": null
            },
            // 3. 信息化项目经济评价实验
            {
                "id": "31",
                "title": "净现值NPV和内部收益率IRR计算-实验",
                "name": "jinxianzhi",
                "kind": "信息化项目绩效评估",
                "content": null,
                "file": null
            },
            {
                "id": "32",
                "title": "动态投资回收期-方法",
                "name": "dongtaitouzi",
                "kind": "信息化项目绩效评估",
                "content": null,
                "file": null
            },
            {
                "id": "33",
                "title": "简单信息化项目绩效评估实验",
                "name": "jixiaopinggu",
                "kind": "信息化项目绩效评估",
                "content": null,
                "file": null
            },
            // 4. 信息化项目风险评估实验
            {
                "id": "41",
                "title": "决策树法-方法",
                "name": "jueceshu",
                "kind": "风险分析与评价",
                "content": null,
                "file": null
            }
        ]
    }
});