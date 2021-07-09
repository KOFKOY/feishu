二维码登录地址：
https://open.feishu.cn/open-apis/authen/v1/index?redirect_uri={REDIRECT_URI}&app_id={APPID}&state={STATE}
https://open.feishu.cn/open-apis/authen/v1/index?redirect_uri=http%3A%2F%2Fwsj.free.aeert.com%2Flogin&app_id=cli_a05687963b38500b&state=wsjtest

#### 1.获取 tenant_access_token/app_access_token
> 调用接口获取企业资源时，需要使用 tenant_access_token 作为授权凭证 有效期为 2 小时自建应用的 app_access_token 等同于 tenant_access_token

`POST`
> https://open.feishu.cn/open-apis/auth/v3/tenant_access_token/internal/
```json
{
    "app_id": "cli_a05687963b38500b",
    "app_secret": "2xYKhGZuOFliwco4fbO7ndIkGwFGlroo"
}
```
返回示例
```json
{
    "code": 0,
    "expire": 2088,
    "msg": "ok",
    "tenant_access_token": "t-63b49fce68a9d3bf68db1950ac6b6877187b9de9"
}
```


#### 2.手机扫码登录 返回code，需要配置重定向RUL
`POST`
> https://open.feishu.cn/open-apis/authen/v1/index?redirect_uri=http%3A%2F%2Fwsj.free.aeert.com%2Flogin&app_id=cli_a05687963b38500b


#### 3.获取用户身份登录凭证user_access_token

`POST`
> https://open.feishu.cn/open-apis/auth/v3/tenant_access_token/internal/

请求头

Authorization  "Bearer + access_token"
Content-Type  "application/json; charset=utf-8"
请求体
```json
{
    "grant_type":"authorization_code",
    "code":"上面返回的code"
}
```
返回示例
```json
{
    "code": 0,
    "data": {
        "access_token": "u-Q6O1CeNX9IzqF6yFqxeeie",
        "avatar_big": "https://s1-fs.pstatp.com/static-resource/v1/eff8d998-2920-491a-b155-dd90c46060dg~?image_size=640x640&cut_type=&quality=&format=image&sticker_format=.webp",
        "avatar_middle": "https://s1-fs.pstatp.com/static-resource/v1/eff8d998-2920-491a-b155-dd90c46060dg~?image_size=240x240&cut_type=&quality=&format=image&sticker_format=.webp",
        "avatar_thumb": "https://s3-fs.pstatp.com/static-resource/v1/eff8d998-2920-491a-b155-dd90c46060dg~?image_size=72x72&cut_type=&quality=&format=image&sticker_format=.webp",
        "avatar_url": "https://s3-fs.pstatp.com/static-resource/v1/eff8d998-2920-491a-b155-dd90c46060dg~?image_size=72x72&cut_type=&quality=&format=image&sticker_format=.webp",
        "email": "",
        "en_name": "王帅杰",
        "expires_in": 6900,
        "mobile": "+8618906660439",
        "name": "王帅杰",
        "open_id": "ou_f4c091cd5dfb69c279d5d9280dc7a175",
        "refresh_expires_in": 2591700,
        "refresh_token": "ur-rZZRU7AsLJMe4m3fnBlKrd",
        "tenant_key": "13b9b129f00f975d",
        "token_type": "Bearer",
        "union_id": "on_36f1cf0f72559e710247ce245811b2f7",
        "user_id": "5dbf1798"
    },
    "msg": "success"
}
```



#### 4.使用返回的用户信息中的 refresh_token刷新access_token
`POST`
> https://open.feishu.cn/open-apis/authen/v1/refresh_access_token

请求头同 3

请求体
```json
{
    "grant_type":"refresh_token",
    "refresh_token":"上面3返回的 refresh_token"
}
```


#### 5.根据用户的access_token获取用户信息
`GET`
> https://open.feishu.cn/open-apis/authen/v1/user_info

Header

 Authorization  Bearer + 用户的access_token
 Content-Type  application/json; charset=utf-8

返回示例
```json
{ 
    "code": 0,
    "msg": "success",
    "data": {
        "name": "张三",
        "en_name": "zhangsan",
        "avatar_url": "www.feishu.cn/avatar/icon",
        "avatar_thumb": "www.feishu.cn/avatar/icon_thumb",
        "avatar_middle": "www.feishu.cn/avatar/icon_middle",
        "avatar_big": "www.feishu.cn/avatar/icon_big",
        "email": "zhangsan@feishu.cn",
        "user_id": "5d9bdxx",
        "mobile": "+86130xxx",
        "open_id": "ou_xxx",
        "union_id": "on_xxx",
        "tenant_key": "736588c92lxf175d"
    }
}
```



#### 6.发送消息

`POST`

receive_id_type=open_id  类型可变
> https://open.feishu.cn/open-apis/im/v1/messages?receive_id_type=open_id
```json
@XXX  艾特用户
{
    "receive_id": "ou_f4c091cd5dfb69c279d5d9280dc7a175",
    "content": "{\"text\":\"<at user_id=\\\"on_36f1cf0f72559e710247ce245811b2f7\\\">Tom</at> test content\"}",
    "msg_type": "text"
}
```



