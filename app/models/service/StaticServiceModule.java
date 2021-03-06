/*
 * Copyright (c) 2014-2015 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package models.service;

import com.google.inject.AbstractModule;

import controllers.security.SecuredSessionOrToken;
import controllers.security.SecuredToken;
import dtos.ApplicationComponentDto;
import dtos.ApplicationInstanceDto;
import dtos.CloudCredentialDto;
import dtos.CloudDto;
import dtos.CloudPropertyDto;
import dtos.CommunicationDto;
import dtos.ComponentHorizontalInScalingActionDto;
import dtos.ComponentHorizontalOutScalingActionDto;
import dtos.ComposedMonitorDto;
import dtos.HardwareDto;
import dtos.ImageDto;
import dtos.InstanceDto;
import dtos.IpAddressDto;
import dtos.KeyPairDto;
import dtos.LocationDto;
import dtos.LoginDto;
import dtos.MonitorInstanceDto;
import dtos.MonitorSubscriptionDto;
import dtos.OperatingSystemDto;
import dtos.PortDto;
import dtos.RawMonitorDto;
import dtos.TenantDto;
import dtos.VirtualMachineDto;
import dtos.VirtualMachineTemplateDto;

/**
 * Created by daniel on 28.04.15.
 */
public class StaticServiceModule extends AbstractModule {

    @Override protected void configure() {

        //static injection in dtos
        requestStaticInjection(ApplicationComponentDto.References.class);
        requestStaticInjection(ApplicationInstanceDto.References.class);
        requestStaticInjection(CloudDto.References.class);
        requestStaticInjection(CloudCredentialDto.References.class);
        requestStaticInjection(CloudPropertyDto.References.class);
        requestStaticInjection(CommunicationDto.References.class);
        requestStaticInjection(ComponentHorizontalInScalingActionDto.References.class);
        requestStaticInjection(ComponentHorizontalOutScalingActionDto.References.class);
        requestStaticInjection(ComposedMonitorDto.References.class);
        requestStaticInjection(LoginDto.References.class);
        requestStaticInjection(TenantDto.References.class);
        requestStaticInjection(HardwareDto.References.class);
        requestStaticInjection(ImageDto.References.class);
        requestStaticInjection(InstanceDto.References.class);
        requestStaticInjection(IpAddressDto.References.class);
        requestStaticInjection(KeyPairDto.References.class);
        requestStaticInjection(LocationDto.References.class);
        requestStaticInjection(MonitorInstanceDto.References.class);
        requestStaticInjection(MonitorSubscriptionDto.References.class);
        requestStaticInjection(RawMonitorDto.References.class);
        requestStaticInjection(PortDto.References.class);
        requestStaticInjection(VirtualMachineDto.References.class);
        requestStaticInjection(VirtualMachineTemplateDto.References.class);

        //static injection in security tokens
        requestStaticInjection(SecuredToken.References.class);
        requestStaticInjection(SecuredSessionOrToken.References.class);

    }
}
