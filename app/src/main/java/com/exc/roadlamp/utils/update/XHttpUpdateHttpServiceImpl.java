/*
 * Copyright (C) 2021 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.exc.roadlamp.utils.update;

import androidx.annotation.NonNull;

import com.exc.roadlamp.utils.XToastUtils;

import com.xuexiang.xupdate.proxy.IUpdateHttpService;

import java.util.Map;

/**
 * XHttp2实现的请求更新
 *
 */
public class XHttpUpdateHttpServiceImpl implements IUpdateHttpService {

    @Override
    public void asyncGet(@NonNull String url, @NonNull Map<String, Object> params, @NonNull final IUpdateHttpService.Callback callBack) {

    }

    @Override
    public void asyncPost(@NonNull String url, @NonNull Map<String, Object> params, @NonNull final IUpdateHttpService.Callback callBack) {

    }

    @Override
    public void download(@NonNull String url, @NonNull String path, @NonNull String fileName, @NonNull final IUpdateHttpService.DownloadCallback callback) {

    }

    @Override
    public void cancelDownload(@NonNull String url) {
        XToastUtils.info("已取消更新");
    }
}