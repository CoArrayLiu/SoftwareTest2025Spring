import http from '../utils/http';
import {Response} from '@/types';

export async function getPdf(id: string) {
    console.log('查看已提交的pdf 当前实验id', id);
    return http.request<any, Response<any>>('/expriment/pdf/${id}', 'get', {id}).then(async (response) => {
        // console.log(response)
        return response;
    });
}

export async function getMatchUser(index: number, role: any) {
    return http.request<any, Response<any>>(`/exp8/matchUser?index=${index}&role=${role}`, 'get', {}).then(async (response) => {
        return response;
    });

}
export async function getMatchUser1(index: number, role: any) {
    return http.request<any, Response<any>>(`/exp8/matchUser1?index=${index}&role=${role}`, 'get', {}).then(async (response) => {
        return response;
    });
}

export async function getSellerOffer() {
    return http.request<any, Response<any>>('/exp8/seller/makePrice', 'post', {}).then(response => {
        return response.data;
    });
}



export async function sendBuyerOffer(purchase: any, offerPrice: number,sale:number) {
    return http.request<any, Response<any>>(`/exp8/buyer/offer?purchase=${purchase}&offerPrice=${offerPrice}&sale=${sale}`, 'get', {}).then(async (response) => {
        return response;
    });
}
export async function sendHigherPrice(offerPrice:number) {
    return http.request<any, Response<any>>(`/exp8/buyer/negotiate?offerPrice=${offerPrice}`, 'get', {}).then(async (response) => {
        return response;
    });
}
//111

export async function getBuyerOffer() {
    return http.request<any, Response<any>>('/exp8/seller/connect', 'post', {}).then(response => {
        return response.data;
    });
}

export async function MakebuyerPrice(situation:string) {
    return http.request<any, Response<any>>(`/exp8/seller/offerSituation?situation=${situation}`, 'get', {}).then(async (response) => {
        return response;
    });
}
export async function BadsituationPrice(price:number) {
    return http.request<any, Response<any>>(`/exp8/seller/offerBadprice?price=${price}`, 'get', {}).then(async (response) => {
        return response;
    });
}
export async function GoodsituationPrice(price:number) {
    return http.request<any, Response<any>>(`/exp8//seller/offerGoodprice?price=${price}`, 'get', {}).then(async (response) => {
        return response;
    });
}