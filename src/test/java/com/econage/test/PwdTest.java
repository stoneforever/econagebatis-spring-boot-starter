package com.econage.test;

import com.flowyun.cornerstone.db.mybatis.spring.boot.datasouce.ConfigTools;
import org.junit.Test;

public class PwdTest {

    static String priKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC0gfRb7SE/MHrcfhNFlD44gUdgJOM38MJYkeYGRbWAp27bBqnECqx8NhNRTuCMjq+qDwBGuIZFD77Kai+OQ9+KmMYDoK+DZ7UVjE7rkTC8sN/LaE/TS80NL8XknQdXJ/9O5+ITyknjYF3ySAWAr/jLGndkJBB8VNugB/VdJiGnE77unpaLIGXF2YBz26kYv7WCeFI1HEDSkQyKa5n5XgnUFqk4P2/3ScZ7NxHZkckYuqINBeD7fOf8rb47n1IyhJ5MWriUAZJN6ZJlhjahufsEb/ZqtGoTtAUXruDiz6xNmyyu0LNujSdm9npZqi5ENMpxUQwADS9fqI0bRk2bGol1AgMBAAECggEAH04ieQbpKzJXPIJJCVoqaFZTcKM1HPCOZBn5kMDqN4PSP/4p1ywaufgO5Z0OaJIeeRnc7Gb4MmYGqsg7syqmTD0uqlZl2h5UU4Zf8Y7eqvrp9TcEERsmVtEgOwPY0A7Jwn+Iv/HnWp3N7UT8X1MljOPsLNvsWAPHizuWHC+OypDgxzqbrcBUAkS81VhU2ekopeVnFiO99sfunPgDYhvDuJMaT6xjXbxxNo89iqIf4Iz8h/J8isfZAHMkdZf9ux9QE/RD+C48Qppf0Zl/mOoUTHYW52xHodKpYtEwd9CJ4fKqSU95zhlhqbR2LiBEQ/RKcm13v+ckBniHDNixowv6SQKBgQD4TjsjN+g08NcE7DD/SgokP8KjmlWZ5iEHhQWQWqYw4i0vnBUOwYSUGv2CzQSbsFtKkCSUb7zAFcdY1P9sVDHDMeFcIJodBk6LxPEoFnPmKG8sYN3yMyTdeKGsTk33cVJAA9v+cZOjF8BWzZ7S1VliiUA7DwwRUlBxBL//bg+SJwKBgQC6GeSfsBsBhQj9TrOT/9rae46x0C7Aoih0RBbh+elnDzgEM3Yf70bR0YHEKW8TIVwHaz5xxVtBnevThfeHYW5Oqh66ohzlYcNsV9jqRa8aV8hQphPu41hw4Pyk8ubXzkNVW1Gc8V73mrTynPjKBH1LYDT8ELJ3tCOvrMLKx+B1AwKBgHzWMAs7z2XWc/QIn3WqDsE8wSmprUHLnm1+Zla2DHnlLJyUF04s4u/nRe9eDeQMXUl2gL2UE8xq5qd1ptka103wpQHBbloLUrAYmrnx1+EuEP650Lw1FGK4hGfxkmdGNPj0N8WL6pMDnPLHPAN5ug+9O2q13PZUcRbxMWpFNlTfAoGBAK2Myh11gKnZI7n+dA+4EaOgiEEgkQkJ8EoE2YYhpl163tVkHGvJm7S9+ZMyxKgFeypcA97bmq/FkxAupdj1roXjtkZ8OhBcLH/UKLDJDXO2m1NCneIw1+BkoJc/ArIMY9StFZ4TpmmoN8jtREI5D5OEg8Tx2U0yi/kfVoT/Az57AoGBAJ9MpwHtdwXT2bdGtWn0575KXDzCkFax6/Fg1off/tKK4nstCcmdlKSGOY05s0l9+oMUmxpHqsWBFQakGDBUG4fbNIwhi6z+WPwwvA9B1RxlOnBDyb7fEqf5dEZO7744uRg7EZbwh2pCYKjNDm+G3i1KQog3SxWxgFBmKsbI+wyk";
    static String pubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtIH0W+0hPzB63H4TRZQ+OIFHYCTjN/DCWJHmBkW1gKdu2wapxAqsfDYTUU7gjI6vqg8ARriGRQ++ymovjkPfipjGA6Cvg2e1FYxO65EwvLDfy2hP00vNDS/F5J0HVyf/TufiE8pJ42Bd8kgFgK/4yxp3ZCQQfFTboAf1XSYhpxO+7p6WiyBlxdmAc9upGL+1gnhSNRxA0pEMimuZ+V4J1BapOD9v90nGezcR2ZHJGLqiDQXg+3zn/K2+O59SMoSeTFq4lAGSTemSZYY2obn7BG/2arRqE7QFF67g4s+sTZssrtCzbo0nZvZ6WaouRDTKcVEMAA0vX6iNG0ZNmxqJdQIDAQAB";

    @Test
    public void test() throws Exception {

        System.out.println(ConfigTools.encrypt(
                priKey,
                "econage123"
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
