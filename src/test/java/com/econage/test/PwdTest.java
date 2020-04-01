package com.econage.test;

import com.flowyun.cornerstone.db.mybatis.spring.boot.datasouce.ConfigTools;
import org.junit.Test;

public class PwdTest {

    static String priKey =
            "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCIjG1L7QKYidTf+ajhZjXk7RUZAQULlGdL/2y0XWtrpATXmNtfXb/yMvlAzfnrvN0/TSA6l20f2Njryqtd3ce9dzPTT52Bicx5DkqcLjjYQ0J/7pa4HPUpaF+84IPfISABmtR1RhA95Jj76znpSSh81IBuCi8G+1/cfVveip0IMpy49UUBQbI0/CDafu5NQ0GHehbivypClDrkGlGzjmlaxSLNeAwHBKEaoBa+f712x79L+RdJrSuIOY4yKV0eIQ58j2bE46T7axYnfEfb2bJhx6Jds8LumwW7DPZfXqlhi2z11EkIOtrjMN/+UpArzWBWtKQ5LmFmRY++IN2Hx6vVAgMBAAECggEAfZqOvxSZGq9OfWgtjIh0cswSaBmkBm7QRfwChjaYpT8OpTMaN7MI6UUyOgY+KjZkXeIYD01TN8REZUDcOU/Ud9e6eFn3zDxxNbmLr/Zkm6ni9cMGKP2aFijIR/lN1GWoB1HgtK9qKUSp5LEPaCO/dnQSXyZG8+ku2KcHZsGiiPKL2kf66LqkO9SR49kC/MsGsbJznEZYdROCbNkVRr5Dn5mmDsaM9OwdG9z02MIbjrmsnlFwttCApBLGssM1Qnn8KHk8mCXFShDRJe+CWqJjVLODLtrVpJFWbgYef9iUMPREZlLY7pcB47KT8qqryZZm7Hj1Z4Kdfp5QniKEZjhP0QKBgQDN/GiLeDGlUNvUsOz1f5bIrBYoNlvsdWbPnG1ZZB19PQD4HjslC48pByHSkGgPkd7gCWTfkKydmxVx1yLuve3dlAY3/+ezjUke4krVRZVtsE+9BI29JFiNovVp/sW2PfRmSGTOu6R6VTglH8aT1umJQsdO9h8E7JAtpdXPJqUxAwKBgQCps/SS3yR7Eg25ZjdA0UiClkkN81S7Zn7ezVNgGN2+R4e0oNBgz1ukLC63dWNnF4YDzC0MFXhguTMUtIjeW+wxuvBV8255sdkUx92egR2ZaFjuMCKW2vYjGPkSoOCdWhR2wkOJlKkPXaBv1fDOQ4VPNaDaWOvWtA6h3AM6PB5cRwKBgGn4lQBLkZ7u/mlprD6QqU4L2kpNzPTVYVMlssvuSY2IuNrtEph4XepJlQjy6dY4wWSY8KBjmaprgpjSiVIxvBHmeVyN9vHUme2dlcoqxjlsH1mq4hCQhk43q/If9C9RqE6Gp/YDaxbUiNHwiwS75+GJzWe+Dy9FVNdnHZw40XtpAoGABDktSgZxovp1CvsnPt62fkF9OfKnVi6b07GqdIbJ5fVjxDQIW5BtiG1tEDXErokgVbHcISPbphSV19sSWO5St34egxUW18J8U48RzeMKuImnRO4/vh9e5sq1UChezquyPK5i/eaMOoyq9H2Pi6KUi78a7LQv0l0SzCZskiNy4fsCgYBxaBHzkd33MPco92xEpYmEHS9uxuv3GwDJWL2yyihrw+hofcsBLIhblo49c3SxxDxfgihXbB+YwDU7FVNuJ2Kzi+ZRmgBc8fE0Or+UcbvI2MEe5oiY4PHVUKoOuW09xUAHnsXmmwXtng2VcUMNF3A/yVbHryOZZ0edE4LIWKvNSg==";
    static String pubKey =
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiIxtS+0CmInU3/mo4WY15O0VGQEFC5RnS/9stF1ra6QE15jbX12/8jL5QM3567zdP00gOpdtH9jY68qrXd3HvXcz00+dgYnMeQ5KnC442ENCf+6WuBz1KWhfvOCD3yEgAZrUdUYQPeSY++s56UkofNSAbgovBvtf3H1b3oqdCDKcuPVFAUGyNPwg2n7uTUNBh3oW4r8qQpQ65BpRs45pWsUizXgMBwShGqAWvn+9dse/S/kXSa0riDmOMildHiEOfI9mxOOk+2sWJ3xH29myYceiXbPC7psFuwz2X16pYYts9dRJCDra4zDf/lKQK81gVrSkOS5hZkWPviDdh8er1QIDAQAB";
    @Test
    public void test() throws Exception {

        System.out.println(ConfigTools.encrypt(
                priKey,
                "adfasdfasfasdf"
        ));
    }

    @Test
    public void test2() throws Exception {
        System.out.println(
                ConfigTools.decrypt(
                        pubKey,
                        "UAGnDGDBE5AwqYIBsF0qWL7UvV9Gd/rgMX8hTEHPRD4mhDOIlui007pAg1j1Et6rH6n8SOrKE6+bTpRDn745HDOhC4UdO+bOacpje0eAdqAfftUmM/mu7dI1hmSGDxF8ejgvbUHH92vyF4k9JeSKUp1TWc6WOiJOluiWcomrH7LoX4Tj5IlLiFjeGUN6CIrlTGplXzDu235Pd3kiZb6Q+bkTLb5sbTmw9NH9gcsike7uc9axN0AYPEc/ZYkoJwLnq/v/Wu+qIps7PIggHEDviFvSGK1jNyLaHi7H75wzG7qbr6fc/QODxrc727u5qfh1r+dk+Q7cid3PMYghwq4GBg=="
                )
        );
    }

}
