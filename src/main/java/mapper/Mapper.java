package mapper;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class Mapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static <S, T> T map(S source, Class<T> targetClass) {
        return modelMapper.map(source, targetClass);
    }

    public static <S, T> List<T> mapList(List<S> sourceList, Class<T> targetClass) {
        return sourceList.stream().map(source -> modelMapper.map(source, targetClass)).collect(Collectors.toList());
    }
}
