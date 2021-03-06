package com.qiniu.android.dns;

import android.test.AndroidTestCase;

import com.qiniu.android.dns.local.AndroidDnsServer;
import com.qiniu.android.dns.local.Resolver;

import junit.framework.Assert;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by bailong on 15/6/21.
 */
public class DnsTest extends AndroidTestCase {
    public void testDns() throws UnknownHostException {
        IResolver[] resolvers = new IResolver[2];
        resolvers[0] = AndroidDnsServer.defaultResolver();
        resolvers[1] = new Resolver(InetAddress.getByName("223.5.5.5"));
        DnsManager dns = new DnsManager(NetworkInfo.normal(), resolvers);
        String[] ips = dns.query("www.qiniu.com");
        assertNotNull(ips);
        assertTrue(ips.length > 0);
    }

    public void testCnc() throws IOException {
        IResolver[] resolvers = new IResolver[2];
        resolvers[0] = AndroidDnsServer.defaultResolver();
        resolvers[1] = new Resolver(InetAddress.getByName("223.5.5.5"));
        DnsManager dns = new DnsManager(new NetworkInfo(NetworkInfo.NetSatus.MOBILE, NetworkInfo.ISP_CNC), resolvers);

        dns.putHosts("hello.qiniu.com", "1.1.1.1");
        dns.putHosts("hello.qiniu.com", "2.2.2.2");
        dns.putHosts("qiniu.com", "3.3.3.3");

        dns.putHosts("qiniu.com", "4.4.4.4", NetworkInfo.ISP_CNC);
        Domain d = new Domain("qiniu.com", false, true);
        String[] r = dns.query(d);
        Assert.assertEquals(1, r.length);
        Assert.assertEquals("4.4.4.4", r[0]);
    }
}
