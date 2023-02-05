//package registration.registrationsystem.service.dto;
//
//import org.springframework.stereotype.Component;
//import registration.registrationsystem.domain.AcademicBlock;
//
//import java.util.List;
//import java.util.stream.Collectors;
//@Component
//public class AcademicBlocksMapper implements Mapper<AcademicBlock, AcademicBlockDto> {
//    @Override
//    public AcademicBlockDto convertToDto(AcademicBlock academicBlock) {
//        return new AcademicBlockDto(academicBlock.getId(), academicBlock.getCode(), academicBlock.getSemester(), academicBlock.getStartDate(), academicBlock.getEndDate());
//    }
//
//    @Override
//    public AcademicBlock convertFromDto(AcademicBlockDto academicBlockDto) {
//        return new AcademicBlock(academicBlockDto.getId(), academicBlockDto.getCode(), academicBlockDto.getSemester(), academicBlockDto.getStartDate(), academicBlockDto.getEndDate(), null);
//    }
//
//    @Override
//    public List<AcademicBlockDto> convertListToDto(List<AcademicBlock> academicBlocks) {
//        return academicBlocks.stream().map(b -> convertToDto(b))
//                .collect(Collectors.toList());
//    }
//}
