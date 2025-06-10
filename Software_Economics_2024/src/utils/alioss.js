
// alioss.js
import OSS from 'ali-oss';

export function client() {
  return new OSS({
    region: '', // 注意：这里的 URL 不需要包含 'aliyuncs.com'
    accessKeyId: '',
    accessKeySecret: '',
    bucket: ''
  });
}