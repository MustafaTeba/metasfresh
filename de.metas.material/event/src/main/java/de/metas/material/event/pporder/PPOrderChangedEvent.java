package de.metas.material.event.pporder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * metasfresh-material-event
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Value
@Builder
public class PPOrderChangedEvent implements MaterialEvent
{
	public static final String TYPE = "PPOrderQtyChangedEvent";

	@NonNull
	private final EventDescriptor eventDescriptor;

	@NonNull
	Date newDatePromised;

	@NonNull
	Date oldDatePromised;

	@NonNull
	ProductDescriptor productDescriptor;

	int ppOrderId;

	@NonNull
	BigDecimal oldQtyRequired;

	@NonNull
	BigDecimal newQtyRequired;

	@NonNull
	BigDecimal oldQtyDelivered;

	@NonNull
	BigDecimal newQtyDelivered;

	@NonNull
	String oldDocStatus;

	@NonNull
	String newDocStatus;

	@Singular
	List<ChangedPPOrderLineDescriptor> ppOrderLineChanges;

	@Singular
	List<DeletedPPOrderLineDescriptor> deletedPPOrderLines;

	@Singular
	List<PPOrderLine> newPPOrderLines;

	@Value
	@Builder
	public static class ChangedPPOrderLineDescriptor
	{
		int oldPPOrderLineId;

		int newPPOrderLineId;

		@NonNull
		ProductDescriptor productDescriptor;

		@NonNull
		Date issueOrReceiveDate;

		@NonNull
		BigDecimal oldQtyRequired;

		@NonNull
		BigDecimal newQtyRequired;

		@NonNull
		BigDecimal oldQtyDelivered;

		@NonNull
		BigDecimal newQtyDelivered;

		public BigDecimal computeOpenQtyDelta()
		{
			final BigDecimal oldOpenQty = oldQtyRequired.subtract(oldQtyDelivered);
			final BigDecimal newOpenQty = newQtyRequired.subtract(newQtyDelivered);

			return newOpenQty.subtract(oldOpenQty);
		}
	}

	@Value
	@Builder
	public static class DeletedPPOrderLineDescriptor
	{
		public static DeletedPPOrderLineDescriptor ofPPOrderLine(
				@NonNull final PPOrderLine ppOrderLine)
		{
			return DeletedPPOrderLineDescriptor.builder()
					.issueOrReceiveDate(ppOrderLine.getIssueOrReceiveDate())
					.ppOrderLineId(ppOrderLine.getPpOrderLineId())
					.productDescriptor(ppOrderLine.getProductDescriptor())
					.qtyRequired(ppOrderLine.getQtyRequired())
					.qtyDelivered(ppOrderLine.getQtyDelivered())
					.build();
		}

		int ppOrderLineId;

		@NonNull
		ProductDescriptor productDescriptor;

		@NonNull
		Date issueOrReceiveDate;

		@NonNull
		BigDecimal qtyRequired;

		@NonNull
		BigDecimal qtyDelivered;
	}
}
