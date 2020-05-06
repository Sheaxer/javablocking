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
/***
 * Class representing ReportedOverlimitTransaction from FENiX - New FrontEnd solution API definition.
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@CurrencyAndCategory(message = "CATEGORY_INVALID")
@Document(collection = "reportedOverlimitTransactions")
public class ReportedOverlimitTransaction {
    /***
     * Internal identifier of order (provided as response after creation from BE)
     */
    @Id
    private String id;

    /***
     * Order category determines whether reported overlimit transaction is withdraw in EUR or foreign currency.
     */
    @NotNull(message = "ORDERCATEGORY_INVALID")
    private OrderCategory orderCategory;
    /***
     * State of order presented to user on FE, value is mapped based on provided BE technical states.
     */
    @NotNull(message = "STATE_INVALID")
    private State state;

    /***
     * Account number of the client (type: IBAN with optional BIC or local account number) where
     * withdraw will be realised.
     */
    @NotNull(message = "SOURCEACCOUNT_INVALID")
    @ValidAccount(message = "SOURCEACCOUNT_INVALID")
    @OnlineAccount(message = "ACCOUNT_OFFLINE")
    private AccountNO sourceAccount;

    /***
     * Object representing client who will realize withdraw. On frontend we have to show client name and dato of birth.
     */
    @DBRef
    @NotNull(message = "CLIENTID_NOT_VALID")
    @JsonDeserialize(using = ClientDeserializer.class)
    @JsonSerialize(using = ClientSerializer.class)
    private Client clientId;

    /***
     * Id of client identification with which will realize withdraw. On frontend we have to show number of
     * identification.
     */
    @NotBlank(message = "IDENTIFICATIONID_INVALID")
    private String identificationId;

    /***
     * Structure for vault. Detail information about withdrow amount.
     */
    @NotEmpty(message = "VAULT_INVALID")
    @Valid
    private List<Vault> vault;

    /***
     * Withdraw amount in defined currency (only EUR for DOMESTIC) and with precision (embedded AMOUNT type).
     */
    @NotNull(message = "FIELD_INVALID")
    @MaxAmount(message = "FIELD_INVALID", maxAmount = 999999999.99)
    @Limit(message = "LIMIT_EXCEEDED")
    @Valid
    private Money amount;

    /***
     * Requested due date entered by client (have to be in near future, minimal D+3),
     * date when withdraw order should be realized from user account.
     * Default value could be current business day +3 ISO date format: YYYY-MM-DD.
     */
    @NotNull(message = "TRANSFERDATE_INVALID")
    @FutureOrPresent(message = "INVALID_DATE_IN_PAST")
    @DaysBeforeDate(message = "FIELD_INVALID_TOO_NEAR_IN_FUTURE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date transferDate;

    private String note;

    /***
     * Modification date indicates the last update of order done by user or BE system
     * (read-only field provided by BE).
     * ISO dateTime format: YYYY-MM-DDThh:mm:ssZ
     */
    @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    private OffsetDateTime modificationDate;

    /***
     * Getter for modificationDate.
     * @return modification date at an offset saved.
     */
    public OffsetDateTime getModificationDate()
    {
        if(this.zoneOffset !=null)
            this.modificationDate = this.modificationDate.toInstant().atOffset(ZoneOffset.of(this.zoneOffset));
        return this.modificationDate;
    }

    public void setModificationDate(OffsetDateTime modificationDate) {
        this.modificationDate = modificationDate;
        this.zoneOffset = modificationDate.getOffset().getId();
    }

    /***
     * Object representing organisation unit where client want to realize withdraw.
     */
    @DBRef
    @NotNull(message = "ORGANISATIONUNITID_NOT_VALID")
    @JsonDeserialize(using = OrganisationUnitDeserializer.class)
    @JsonSerialize(using = OrganisationUnitSerializer.class)
    private OrganisationUnit organisationUnitID;

    /***
     * Object representing employer who entered an transaction. In this case report over limit withdraw.
     */
    @DBRef
    @NotNull(message = "CREATEDBY_NOT_VALID")
    @JsonDeserialize(using = EmployeeDeserializer.class)
    @JsonSerialize(using = EmployeeSerializer.class)
    private Employee createdBy;

    @JsonIgnore
    private String zoneOffset="+00:00";

}
