package xyz.korme.handaccount.service.userIdToUniqueNumber;

import org.springframework.stereotype.Service;

@Service
public interface UserIdToUniqueNumber {
    int getUserId(int uniqueNumber);
    int getUniqueNumber(int userId);
}
