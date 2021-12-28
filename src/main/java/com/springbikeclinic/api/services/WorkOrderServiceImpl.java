package com.springbikeclinic.api.services;

import com.springbikeclinic.api.domain.WorkOrder;
import com.springbikeclinic.api.repositories.WorkOrderRepository;
import com.springbikeclinic.api.web.mappers.WorkOrderMapper;
import com.springbikeclinic.api.web.model.WorkOrderDto;
import com.springbikeclinic.api.web.model.WorkOrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class WorkOrderServiceImpl implements WorkOrderService {

    private final WorkOrderRepository workOrderRepository;
    private final WorkOrderMapper workOrderMapper;

    @Override
    @Transactional(readOnly = true)
    public WorkOrderDto getById(final Long id) {
        return workOrderRepository.findById(id) // find returns Optional<T> whereas get (deprecated) returns item or exception if not found
                .map(workOrderMapper::workOrderToWorkOrderDto)
                .orElseThrow(() -> new NotFoundException(String.format("Work Order ID %s was not found", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkOrderDto> getByStatus(final String status) {
        return workOrderRepository.findAllByStatus(status)
                .stream()
                .map(workOrderMapper::workOrderToWorkOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long createWorkOrder(WorkOrderRequest workOrderRequest) {
        final WorkOrder mappedWorkOrder = workOrderMapper.workOrderRequestToWorkOrder(workOrderRequest);
        final WorkOrder savedWorkOrder = workOrderRepository.save(mappedWorkOrder);
        return savedWorkOrder.getId();
    }

    @Override
    @Transactional
    public WorkOrderDto updateWorkOrder(Long id, WorkOrderDto workOrderDto) {
        WorkOrder existingWorkOrder = workOrderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Work Order not found for Id %s", id)));

        existingWorkOrder.setBikeDescription(workOrderDto.getBikeDescription());
        existingWorkOrder.setWorkDescription(workOrderDto.getWorkDescription());
        existingWorkOrder.setMechanicNotes(workOrderDto.getMechanicNotes());
        existingWorkOrder.setStatus(workOrderDto.getStatus());

        WorkOrder result = workOrderRepository.save(existingWorkOrder);

        return workOrderMapper.workOrderToWorkOrderDto(result);
    }

    @Override
    @Transactional
    public WorkOrderDto patchWorkOrder(Long id, Map<String, Object> patch) {
        WorkOrder existingWorkOrder = workOrderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Work Order not found for Id %s", id)));

        patch.forEach( (key, value) -> {
            Field field = ReflectionUtils.findField(WorkOrder.class, key);
            if(field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, existingWorkOrder, value);
            }
        });

        WorkOrder result = workOrderRepository.save(existingWorkOrder);

        return workOrderMapper.workOrderToWorkOrderDto(result);
    }

    @Override
    public void deleteWorkOrder(Long id) {
        try {
            workOrderRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
            throw new NotFoundException(String.format("Work Order not found for Id %s", id));
        }
    }

}

