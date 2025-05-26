package com.gerenciarh.gerenciarh.Utils;


import com.gerenciarh.gerenciarh.DtosResponse.DepartamentoDtoResponse;
import com.gerenciarh.gerenciarh.Models.Department;
import java.util.List;

public class DepartamentoUtils {

    public static List<DepartamentoDtoResponse> listDtoFromModel(List<Department> departments) {
        return  departments.stream()
                    .map(dpt -> new DepartamentoDtoResponse(
                            dpt.getname()
                    )).toList();
    }

    public static DepartamentoDtoResponse ModelFromDto(Department department) {
        return new DepartamentoDtoResponse(department.getname());
    }
}
