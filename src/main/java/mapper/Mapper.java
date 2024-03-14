package mapper;

import dto.customer.CustomerDTO;
import entity.Customer;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class Mapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static <S, T> T map(S source, Class<T> targetClass) {
        return modelMapper.map(source, targetClass);
    }

    static {
        modelMapper.createTypeMap(Customer.class, CustomerDTO.class)
                .addMappings(mapper -> mapper.using(countTransactions()).map(Customer::getTransactions, CustomerDTO::setTransactionCount));
    }

    public static <S, T> List<T> mapList(List<S> sourceList, Class<T> targetClass) {
        return sourceList.stream().map(source -> modelMapper.map(source, targetClass)).collect(Collectors.toList());
    }

    private static Converter<List<?>, Integer> countTransactions() {
        return ctx -> ctx.getSource().size();
    }
}
