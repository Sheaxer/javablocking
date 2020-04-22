package stuba.fei.gono.java.blocking.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import stuba.fei.gono.java.blocking.json.*;
import stuba.fei.gono.java.json.OffsetDateTimeDeserializer;
import stuba.fei.gono.java.json.*;
import stuba.fei.gono.java.pojo.*;
import stuba.fei.gono.java.blocking.validation.annotations.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@CurrencyAndCategory(message = "CATEGORY_INVALID")
@Document(collection = "reportedOverlimitTransactions")
public class ReportedOverlimitTransaction {

    @Id
    private String id;

    @NotNull(message = "ORDERCATEGORY_INVALID")
    private OrderCategory orderCategory;

    private State state;

    @NotNull(message = "SOURCEACCOUNT_INVALID")
    @ValidAccount(message = "SOURCEACCOUNT_INVALID")
    @OnlineAccount(message = "ACCOUNT_OFFLINE")
    private Account sourceAccount;

    @DBRef
    @NotNull(message = "CLIENTID_NOT_VALID")
    @Valid
    @JsonDeserialize(using = ClientDeserializer.class)
    @JsonSerialize(using = ClientSerializer.class)
    private Client clientId;

    @NotBlank(message = "IDENTIFICATIONID_INVALID")
    private String identificationId;

    @NotNull(message = "FIELD_INVALID")
    @MaxAmount(message = "FIELD_INVALID", maxValue = 999999999.99)
    @Limit(message = "LIMIT_EXCEEDED")
    private Money amount;

    //@NotNull(message = "VAULT_INVALID")
    @NotEmpty(message = "VAULT_INVALID")
    private List<Vault> vault;

    @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    private OffsetDateTime modificationDate;

    public OffsetDateTime getModificationDate()
    {
        if(this.zoneOffset !=null)
            this.modificationDate = this.modificationDate.toInstant().atOffset(ZoneOffset.of(this.zoneOffset));
        return this.modificationDate;
    }

    //@Past(message = "INVALID_DATE_IN_PAST")
    //@BankingDay(message = "INVALID_DATE")
    @NotNull(message = "TRANSFERDATE_INVALID")
    @FutureOrPresent(message = "INVALID_DATE_IN_PAST")
    @DaysBeforeDate(message = "FIELD_INVALID_TOO_NEAR_IN_FUTURE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date transferDate;

    private String note;

    @DBRef
    @NotNull(message = "ORGANISATIONUNITID_NOT_VALID")
    @JsonDeserialize(using = OrganisationUnitDeserializer.class)
    @JsonSerialize(using = OrganisationUnitSerializer.class)
    private OrganisationUnit organisationUnitID;

    @DBRef
    @NotNull(message = "CREATEDBY_NOT_VALID")
    @JsonDeserialize(using = EmployeeDeserializer.class)
    @JsonSerialize(using = EmployeeSerializer.class)
    private Employee createdBy;

    @JsonIgnore
    private String zoneOffset;
    /*@PersistenceConstructor
    public ReportedOverlimitTransaction(String id, OrderCategory orderCategory, State state, Account sourceAccount,
                                        Client clientId, String identificationId, Money amount, )

*/
}
