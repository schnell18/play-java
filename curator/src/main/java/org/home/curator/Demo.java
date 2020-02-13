package org.home.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Demo {
    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        String connStr = "zk.dev.jjhome.com:2181";
        CuratorFramework client = CuratorFrameworkFactory.newClient(connStr, retryPolicy);
        client.start();
        String path = "/dubbo/com.jjhome.luka.api.RescueService/providers";
        List<String> children = client.getChildren().forPath(path);
        for(String child : children) {
            URI dubbo = parseDubboProviderURI(child);
            System.out.println(dubbo.getScheme());
            System.out.println(dubbo.getHost());
            System.out.println(dubbo.getPort());
        }

        client.close();
    }

    private static URI parseDubboProviderURI(String uri) throws UnsupportedEncodingException, URISyntaxException {
        return new URI(URLDecoder.decode(uri, StandardCharsets.UTF_8.toString()));
    }
}