// var OSS = require('ali-oss');
// export function client() {
//   var client = new OSS({
//     region: 'oss-cn-shanghai.aliyuncs.com',
//     accessKeyId: 'LTAI5t5dcFimZP6exFTKFJce',
//     accessKeySecret: 'EkoEGChsbD3WMxxkrfD7oiqBOchxms',
//     bucket: 'tjvse'
//   })  //后端提供数据
//   return client
// }

// alioss.js
import OSS from 'ali-oss';

export function client() {
  return new OSS({
    region: 'oss-cn-shanghai', // 注意：这里的 URL 不需要包含 'aliyuncs.com'
    accessKeyId: 'LTAI5t5dcFimZP6exFTKFJce',
    accessKeySecret: 'EkoEGChsbD3WMxxkrfD7oiqBOchxms',
    bucket: 'tjvse'
  });
}