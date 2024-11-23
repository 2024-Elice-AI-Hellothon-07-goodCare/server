package com.goodcare.server.domain.caregiver.controller;

import com.goodcare.server.domain.caregiver.dto.caregiver.CaregiverDTO;
import com.goodcare.server.domain.caregiver.dto.caregiver.CaregiverRegisterDTO;
import com.goodcare.server.domain.caregiver.service.CaregiverInfoService;
import com.goodcare.server.domain.patient.dto.patientinfodto.RegisterDTO;
import com.goodcare.server.global.response.ApiResponse;
import com.goodcare.server.global.response.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/caregiver/info")
@Tag(name = "Caregiver_Info", description = "간병인 DB 관련 api")
@AllArgsConstructor
public class CaregiverInfoController {

    private final CaregiverInfoService caregiverInfoService;

    @PostMapping("/save")
    @Operation(
            summary = "간병인 정보 가입 API",
            description = "간병인 회원가입시 정보를 받아 DB에 저장합니다."
    )
    public ApiResponse<?> saveCaregiver(@RequestBody CaregiverDTO caregiverDTO)
    {
        CaregiverRegisterDTO result = caregiverInfoService.saveCaregiver(caregiverDTO);
        
        if(!result.getSuccess())
            return ApiResponse.onFailure(Status.CONFLICT.getCode(),
                    Status.CONFLICT.getMessage(), result);
        else
            return ApiResponse.onSuccess(Status.OK.getCode(),
                    Status.OK.getMessage(), result);
    }

    @GetMapping("/get/patient-name")
    @Operation(
            summary = "환자 이름을 얻어옵니다",
            description = "간병인 코드를 이용해 환자 이름을 얻어옵니다."
    )
    public ApiResponse<?> getPatientName(String code){
        String name = caregiverInfoService.getPatientNameByCaregiverCode(code);
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), name);
    }
}
