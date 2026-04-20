package com.joker.edu.client;


import com.joker.commonutils.response.R;
import com.joker.edu.client.impl.VodFileDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(value = "service-vod",fallback = VodFileDegradeFeignClient.class )
public interface VodClient {

    @DeleteMapping(value = "/eduvod/vod/removeAlyVideo/{id}")
    public R removeVideo(@PathVariable("id") String videoId);

    @DeleteMapping("/eduvod/vod/deleteBatch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);

}
