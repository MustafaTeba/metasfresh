package de.metas.costing;

import java.util.List;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_CostDetail;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface ICostDetailRepository extends ISingletonService
{
	void save(I_M_CostDetail costDetail);

	void delete(I_M_CostDetail costDetail);

	void delete(CostDetailQuery query);

	void deleteUnprocessedWithNoChanges(CostDetailQuery query);

	I_M_CostDetail getCostDetailOrNull(CostDetailQuery query);

	List<I_M_CostDetail> getAllForDocument(CostingDocumentRef documentRef);

	boolean hasCostDetailsForProductId(int productId);

}
