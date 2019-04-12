package xyz.korme.handaccount.service.userIdToUniqueNumber;

import org.springframework.stereotype.Service;

@Service
public class UserIdToUniqueNumberMPL implements UserIdToUniqueNumber {
    @Override
    public int getUserId(int uniqueNumber) {
        return uniqueNumber-10000;
    }

    @Override
    public int getUniqueNumber(int userId) {
        return userId+10000;
    }
}
