import axios from 'axios';
import { message } from 'ant-design-vue';

export interface ML_tree_params_type{
  type: string,
  splitter: string,
  max_depth: number,
  proportion: number,
}

export interface ML_tree_result_type{
  imageUrl: string,
  score: number,
  time: number,
  depth: number,
}

export async function generateMLtree(ML_tree_params:ML_tree_params_type){
  try{
    const url = 'http://localhost:5000/api/decisionTree/MLTree'
    const response = await axios.post(url,
      {
        data:
          {
            "type": ML_tree_params.type,
            "splitter": ML_tree_params.splitter,
            "max_depth": ML_tree_params.max_depth,
            "proportion": ML_tree_params.proportion
          }
      },
      {
        responseType: 'blob'
      })
    message.success('决策树生成成功',2);
    return {
      imageUrl: "data:image/png;base64," + response.data.image,
      score: response.data.score,
      time: response.data.time,
      depth: response.data.depth,
    }
  }
  catch(error){
    message.error('网络请求出错，无法生成决策树',2);
  }
}

export async function generateTraditionaltree(csv_data){
    try{
      const url = 'http://localhost:5000/api/decisionTree/traditionalTree'
      const response = await axios.post(url,
        {
          data:
            {
             "csv_data": csv_data,
            }
        },
        {
          responseType: 'blob'
        })
      message.success('决策树生成成功',2);
      return {
        imageUrl: "data:image/png;base64," + response.data.image,
      }
    }
    catch(error){
      message.error('网络请求出错，无法生成决策树',2);
    }
  }